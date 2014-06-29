package fh.tagmon.client.clientEngine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fh.tagmon.client.Helper_PlayerSettings;
import fh.tagmon.client.gui.Fight;
import fh.tagmon.client.gui.GuiPartsToUpdate;
import fh.tagmon.client.gui.ISetAbility;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.helperobjects.SummaryObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.clientConnections.ANetworkConnection;
import fh.tagmon.network.clientConnections.NetworkSocketConnection;
import fh.tagmon.network.message.MessageFactory;
import fh.tagmon.network.message.MessageObject;

public class GameClientEngine extends AsyncTask <Void, Void, Void> implements Observer, ISetAbility{
	
	private Context context;
    private final Object waitForPlayer;
    private boolean wait = true;
    private final Object waitForOtherDialog;
    private boolean waitForDialog = false;
    private ActionObject choosenAbility;
    
    //TODO durch zentralgespeicherte eigene player-ID ersetzen
    private int ID = 0;
    private List<PlayerInfo> players = null;

    //GAMEPLAY
	private MonsterPlayModule monster;
	
	//NETWORK
	private ANetworkConnection connection;
    private ConnectionType type;
	
	public GameClientEngine(Context context, MonsterPlayModule mPM, ConnectionType type){
		this.context = context;
		this.monster = mPM;
        this.type = type;
        waitForPlayer = new Object();
        waitForOtherDialog = new Object();
	}

    @Override
    protected Void doInBackground(Void... params) {
        connectToNetwork(type);
		return null;
    }

    private void connectToNetwork(ConnectionType type){
		switch(type){
		case BLUETOOTH:
			break;
		case LCL_SOCKET:
			try {
				connection = new NetworkSocketConnection("localhost");

			//	t = new Thread(connection);
	        } catch (IOException e) {
	            Log.e("Client localhost connection", e.getMessage());
	            connection = null;
	        }
			break;
		case TCP_SOCKET:
			break;
		default:
			break;
		}
		if(connection != null){
		    listenToBroadcast();
		}
    }


    private void listenToBroadcast(){
        boolean running = true;
        while(running) {
            MessageObject<?> obj= connection.listenToBroadcast();
            update(null, obj);
        }
    }

	@Override
	public void update(Observable observable, Object hostMsg) {
		MessageObject<?> msg = (MessageObject<?>) hostMsg;
        switch(msg.messageType){
        case GAME_START:
            proceedGameStart(msg);
            break;
		case SUMMARY:
            proceedSummary(msg);
			break;
		case YOUR_TURN:
            proceedYourTurn(msg);
			break;
		case ABILITY_COMPONENT:
			proceedIncomingAbilityComponent(msg);
			break;
		case GAME_OVER:
			stop(); 
			break;
		default:
			((Fight)context).showTemporaryDialog("Ungltige Host-Message", this);
			break;
		}
	}
	
	private void proceedGameStart(MessageObject<?> msg){
		this.ID = (Integer) msg.getContent();
        PlayerInfo startInfo = new PlayerInfo(Helper_PlayerSettings.playerName, ID);
        startInfo.setCurrentLife(monster.getMonster().getCurrentLifePoints());
        startInfo.setMaxLife(monster.getMonster().getMaxLifePoints());
        this.connection.sendToHost(MessageFactory.createClientMessage_GameStart(startInfo, 0));
	}
	
	private void proceedSummary(MessageObject<?> msg){
		if(this.players == null)
			prepareFightGUI((List <PlayerInfo>) ((SummaryObject)msg.getContent()).getPlayerInfos());
		
		SummaryObject summary = (SummaryObject)msg.getContent();
        ((Fight)context).refreshGUI(summary.getPlayerInfos(), GuiPartsToUpdate.HEALTH);

        Log.d("summary", "got summary");
        synchronized (waitForOtherDialog) {
            while (waitForDialog) {
                try {
                    waitForOtherDialog.wait();
                } catch (InterruptedException e) { }
            }
        }
        ((Fight)context).showTemporaryDialog(summary.getSummary(), this);

        onPauseDialog();
	}
	private void prepareFightGUI(List <PlayerInfo> players){
		this.players = players;
        List<Ability> abilitylist = monster.getMonster().getAbilitys();
        ((Fight) context).initBattleGUI(players, ID, abilitylist);
	}
    /**
     * Call this on pause.
     */
    public void onPauseDialog() {
        synchronized (waitForOtherDialog){ waitForDialog = true; }
    }
	
	private void proceedYourTurn(MessageObject<?> msg){
		Log.d("your Turn", "player turn");
        this.monster.newRound(players, ID);
        onPause();
		ActionObject actionObject = waitForAction();

		connection.sendToHost(MessageFactory.createClientMessage_Action(actionObject, ID));
	}
    /**
     * Call this on pause.
     */
    public void onPause() {
        synchronized (waitForPlayer){ wait = true; }
    }
	private ActionObject waitForAction() {
        synchronized (waitForOtherDialog) {
            while (waitForDialog) {
                try {
                    waitForOtherDialog.wait();
                } catch (InterruptedException e) { }
            }
        }
        ActionObject action = null;
        ((Fight) context).chooseAbility(this);
        synchronized (waitForPlayer) {
        	while (wait) {
        		try {
        			waitForPlayer.wait();
        		} catch (InterruptedException e) { }
        	}
        }
        action = choosenAbility;
        return action;
    }
	
	private void proceedIncomingAbilityComponent(MessageObject<?> msg){
		AbilityComponentDirector director = monster.getMonstersAbilityComponentDirector();
		AbilityComponentList abilityComponents = (AbilityComponentList) msg.getContent();
		PlayerInfo info = new PlayerInfo(Helper_PlayerSettings.playerName, ID);
		AnswerObject answerObject = director.handleAbilityComponents(abilityComponents, info);
		connection.sendToHost(MessageFactory.createClientMessage_Answer(answerObject, ID));
	}
	
	private void stop(){
		connection.deleteObservers();
		connection.closeConnection();
        ((Fight) context).handleGameOver("Game finished");
	}
	


    @Override
    public void setAbility(ActionObject actionObject) {
        choosenAbility = actionObject;
        onResume();
    }
    /**
     * Call this on resume.
     */
    public void onResume() {
        synchronized (waitForPlayer) {
            wait = false;
            waitForPlayer.notifyAll();
        }
    }


    /**
     * Call this on resume.
     */
    public void onResumeDialog() {
        synchronized (waitForOtherDialog) {
            waitForDialog = false;
            waitForOtherDialog.notifyAll();
        }
    }
}
