package fh.tagmon.client.clientEngine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fh.tagmon.client.Helper_PlayerSettings;
import fh.tagmon.client.gui.Fight;
import fh.tagmon.client.gui.GuiPartsToUpdate;
import fh.tagmon.client.gui.ISetAbility;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.*;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.clientConnections.*;
import fh.tagmon.network.message.*;

public class GameClientEngine extends AsyncTask <Void, Void, Void> implements ISetAbility{
	
		//GUI-HANDLING
		private Context context;
	    private final Object waitForPlayer;
	    private boolean wait = true;
	    private final Object waitForOtherDialog;
	    private boolean waitForDialog = false;
	    
	    //USER-INFOS
	    private int ID = 0;
	    private List<PlayerInfo> players = null; //TODO should be replaced by saved user informations. -> database
	
	    //GAMEPLAY
		private MonsterPlayModule monster;
	    private ActionObject choosenAbility;
		
		//NETWORK
		private ANetworkConnection connection;
	    private ConnectionType connectionType;
	
	public GameClientEngine(Context context, MonsterPlayModule mPM, ConnectionType type){
		this.context 		= context;
		this.monster 		= mPM;
        this.connectionType = type;
        waitForPlayer 		= new Object();
        waitForOtherDialog 	= new Object();
	}

    @Override
    protected Void doInBackground(Void... params) {
        connectToNetwork(connectionType);
		if(connection != null) 
			listenToBroadcast();
		return null;
    }

    /**
     * This method is ment to establish a network-connection based on the connectiontype.
     * Connectiontypes, which are planed to be supported are:
     * Bluetooth, TCP-Socket-Connection, Localhost-Connection (TPC-Socket).
     * @param ConnectionType type
     */
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
    }

    private boolean listeningToBroadcast = true;
    /**
     * This method uses the previously established network connection as long as the member-variable "listenToBroadcast"
     * remain true. Right after that have happened, listenToBroadcast() closes the opened connection.
     * <br/>
     * Each received Host-Message is proceeded to the handleInput(MessageObject<?> msg)-method.
     */
    private void listenToBroadcast(){
        while(listeningToBroadcast) {
            MessageObject<?> obj= connection.listenToBroadcast();
            handleInput(obj);
        }
        connection.closeConnection();
    }

    /**
     * handleInput is called by listenToBroadcast() whenever a host-message was received. The handleInput(msg)
     * just delegates the message in order to guarantee its correct handling.
     * @param msg
     */
	private void handleInput(MessageObject<?> msg) {
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
	
	/**
	 * This method is called one before the actually game begin. Its neccesary to prepare mandatory data.
	 */
	private void proceedGameStart(MessageObject<?> msg){
		this.ID = (Integer) msg.getContent();
        PlayerInfo startInfo = new PlayerInfo(Helper_PlayerSettings.playerName, ID);
        startInfo.setCurrentLife(monster.getMonster().getCurrentLifePoints());
        startInfo.setMaxLife(monster.getMonster().getMaxLifePoints());
        this.connection.sendToHost(MessageFactory.createClientMessage_GameStart(startInfo, 0));
	}
	
	/**
	 * This method is called right before the first turn is done and right after that, whenever the 
	 * current Turn is finished. The in messageobject enclosed summaryobject contains updated 
	 * user-informations of each and every player, who was influenced by the currently handled
	 * monster-ability.
	 */
	private void proceedSummary(MessageObject<?> msg){
		if(this.players == null)
			prepareFightGUI((List <PlayerInfo>) ((SummaryObject)msg.getContent()).getPlayerInfos());
		
		SummaryObject summary = (SummaryObject)msg.getContent();
        ((Fight)context).refreshGUI(summary.getPlayerInfos(), GuiPartsToUpdate.HEALTH);

        Log.d("summary", "got summary");

        String summaryMsg = summary.getSummary();
        if(!summaryMsg.isEmpty())
        	((Fight)context).showTemporaryDialog(summary.getSummary(), this);

        //onPauseDialog();
	}
	
	/**
	 * This method is called only once before the first turn is handled.
	 * It is ment to establish the monster's abilities and initializes the GUI.
	 * @param players
	 */
	private void prepareFightGUI(List <PlayerInfo> players){
		this.players = players;
        List<Ability> abilitylist = monster.getMonster().getAbilitys();
        ((Fight) context).initBattleGUI(players, ID, abilitylist);
	}
    /**
     * Call this on pause.
     * @author Pascal
     */
    public void onPauseDialog() {
        synchronized (waitForOtherDialog){ waitForDialog = true; }
    }
	
    /**
     * proceedYourTurn(MessageObject<?> msg) handles the host-requests, which are sent 
     * whenever the player is ment to choose a monster-ability.
     * @param msg
     */
	private void proceedYourTurn(MessageObject<?> msg){
		Log.d("your Turn", "player turn");
        this.monster.newRound(players, ID);
        onPause();
		ActionObject actionObject = waitForAction();

		connection.sendToHost(MessageFactory.createClientMessage_Action(actionObject, ID));
	}
    /**
     * Call this on pause.
     * @author Pascal
     */
    public void onPause() {
        synchronized (waitForPlayer){ wait = true; }
    }
    /**
     * @author Pascal
     * @return actionObject
     */
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
	 * This method is called whenever one of your own monster's abilitys or any other monster's abilities affects
	 * the client. The method delegates the contained abilitycomponentList to the MonsterAbilityComponentDirector
	 * and expects an AnswerObject in return, which is sent back to the hist right after that.
	 * @param msg
	 */
	private void proceedIncomingAbilityComponent(MessageObject<?> msg){
		AbilityComponentDirector director = monster.getMonstersAbilityComponentDirector();
		AbilityComponentList abilityComponents = (AbilityComponentList) msg.getContent();
		PlayerInfo info = new PlayerInfo(Helper_PlayerSettings.playerName, ID);
		AnswerObject answerObject = director.handleAbilityComponents(abilityComponents, info);
		connection.sendToHost(MessageFactory.createClientMessage_Answer(answerObject, ID));
	}
	
	/**
	 * Displays the "Game finished"-Screen
	 */
	private void stop(){
        listeningToBroadcast = false;
        ((Fight) context).handleGameOver("Game finished");
	}

	/**
	 * stops the entire client
	 */
    public void closeGame() {

    }
	
    /**
     * @author Pascal
     */
    @Override
    public void setAbility(ActionObject actionObject) {
        choosenAbility = actionObject;
        onResume();
    }
    /**
     * Call this on resume.
     * @author Pascal
     */
    public void onResume() {
        synchronized (waitForPlayer) {
            wait = false;
            waitForPlayer.notifyAll();
        }
    }


    /**
     * Call this on resume.
     * @author Pascal
     */
    public void onResumeDialog() {
        synchronized (waitForOtherDialog) {
            waitForDialog = false;
            waitForOtherDialog.notifyAll();
        }
    }
}
