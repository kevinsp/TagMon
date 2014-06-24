package fh.tagmon.client.clientEngine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fh.tagmon.client.Helper_PlayerSettings;
import fh.tagmon.client.gui.*;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.*;
import fh.tagmon.gameengine.helperobjects.*;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.clientConnections.*;
import fh.tagmon.network.message.*;

public class GameClientEngine extends AsyncTask <Void, Void, Void> implements Observer, ISetAbility{
	
	private Context context;
    private final Object waitForPlayer;
    private boolean wait;
    private ActionObject choosenAbility;
    
    //TODO durch zentralgespeicherte eigene player-ID ersetzen
    private int ID = 0;
	
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
		if(connection != null)
            connection.addObserver(this);
    }

	private void stop(){
		connection.deleteObservers();
		connection.closeConnection();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object hostMsg) {
		boolean firstTurn = true;
		
		MessageObject<?> msg = (MessageObject<?>) hostMsg;
        List<PlayerInfo> players;
        switch(msg.messageType){
		case ABILITY_COMPONENT:
			AbilityComponentList abilityComponents = (AbilityComponentList) msg.getContent();
			PlayerInfo info = new PlayerInfo(Helper_PlayerSettings.playerName, ID);
			AbilityComponentDirector director = monster.getMonstersAbilityComponentDirector();
			AnswerObject answerObject = director.handleAbilityComponents(abilityComponents, info);
			connection.sendToHost(MessageFactory.createClientMessage_Answer(answerObject, ID));
			break;
		case SUMMARY:
			SummaryObject summary = (SummaryObject)msg.getContent();
			((Fight)context).showTemporaryDialog(summary.getSummary());
            ((Fight)context).refreshGUI(summary.getPlayerInfos(), GuiPartsToUpdate.HEALTH);
			break;
		case GAME_OVER:
			stop(); 
			//Statistik ausgeben und zum Hauptbildschirm zurck.
			//Optional: hchster Damage, gespielte Runden bla bla mitloggen..
			break;
		case YOUR_TURN:
			 players = (List <PlayerInfo>) msg.getContent();
			if(firstTurn){
				List<Ability> abilitylist = monster.getMonster().getAbilitys();
				((Fight) context).initBattleGUI(players, ID, abilitylist);
			}
            ((Fight) context).refreshGUI(players, GuiPartsToUpdate.HEALTH);
			ActionObject actionObject = waitForAction();
			connection.sendToHost(MessageFactory.createClientMessage_Action(actionObject, ID));
			break;
        case GAME_START:
            this.connection.sendToHost(MessageFactory.createClientMessage_GameStart(Helper_PlayerSettings.playerName, 0));
            this.ID = (Integer) msg.getContent();
            break;
		default:
			((Fight)context).showTemporaryDialog("Ungültige Host-Message");
			break;
		}
	}
	
	private ActionObject waitForAction() {
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


    /**
     * Call this on pause.
     */
    public void onPause() {
        synchronized (waitForPlayer){ wait = true; }
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

    @Override
    public void setAbility(ActionObject actionObject) {
        choosenAbility = actionObject;
        onResume();
    }
}
