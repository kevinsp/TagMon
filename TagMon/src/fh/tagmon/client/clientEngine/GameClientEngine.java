package fh.tagmon.client.clientEngine;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import fh.tagmon.client.gui.Fight;
import fh.tagmon.client.gui.ISetAbility;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.clientConnections.ANetworkConnection;
import fh.tagmon.network.clientConnections.NetworkSocketConnection;
import fh.tagmon.network.message.MessageFactory;
import fh.tagmon.network.message.MessageObject;

;

public class GameClientEngine implements Observer, ISetAbility{
	
	private Context context;
    private final Object waitForPlayer;
    private boolean wait;
    private ActionObject choosenAbility;
    
    //TODO durch zentralgespeicherte eigene player-ID ersetzen
    private final int ID = 0;
	
	//GAMEPLAY
	private MonsterPlayModule monster;
	
	//NETWORK
	private ANetworkConnection connection;
	
	public GameClientEngine(Context context, MonsterPlayModule mPM, ConnectionType type){
		this.context = context;
		this.monster = mPM;
        waitForPlayer = new Object();
        connectToNetwork(type);
	}
	
	private void connectToNetwork(ConnectionType type){
		switch(type){
		case BLUETOOTH:
			break;
		case LCL_SOCKET:
			try {
				connection = new NetworkSocketConnection("localhost");
			} catch (IOException e) {
				Log.e("Client localhost connection", "Connection failed!");
				connection = null;
			}
			break;
		case TCP_SOCKET:
			break;
		default:
			break;
		}
		if(connection != null) {
            connection.addObserver(this);
            //TODO: list with players and the id of the client
            //TODO: list with abilitys!
            ((Fight) context).initBattleGUI( players, userId, abilitylist);
        }
	}
	
	private void stop(){
		connection.deleteObservers();
		connection.closeConnection();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object hostMsg) {
		MessageObject<?> msg = (MessageObject<?>) hostMsg;
		switch(msg.messageType){
		case ABILITY_COMPONENT:
			AbilityComponentList abilityComponents = (AbilityComponentList) msg.getContent();
			AnswerObject answerObject = monster.getMonstersAbilityComponentDirector().handleAbilityComponents(abilityComponents);
			connection.sendToHost(MessageFactory.createClientMessage_Answer(answerObject, ID));
			break;
		case SUMMARY:
			//TODO Durch korrekte Methode ersetzen
			((Fight)context).showTemporaryDialog((String) msg.getContent());
			break;
		case GAME_OVER:
			stop(); 
			//Statistik ausgeben und zum Hauptbildschirm zurck.
			//Optional: hchster Damage, gespielte Runden bla bla mitloggen..
			break;
		case YOUR_TURN:
			HashMap<Integer, PlayerInfo> playerMap = (HashMap<Integer, PlayerInfo>) msg.getContent();
			
            //TODO nicht jedes mal die gui initialisieren -> UPDATEN
            //TODO:  int id, final Enum<GuiPartsToUpdate> partToUpdate, Object value
            //TODO: call for every part which should be updated, for example own life, enemy life etc
			((Fight) context).refreshGUI(ID, partToUpdate, value);
			ActionObject actionObject = waitForAction(playerMap);
			connection.sendToHost(MessageFactory.createClientMessage_Action(actionObject, ID));
			break;
		default:
			//TODO evtl Meldung auf Screen ausgeben
			break;
		}
	}
	
	private ActionObject waitForAction(HashMap<Integer, PlayerInfo> playerMap) {
        ActionObject action = null;


      //  if (currentPlayer.getId() == 0) {
            onPause();




            ((Fight) context).chooseAbility(this);

            //currentPlayer.sendNewRoundEvent(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());

            synchronized (waitForPlayer) {
                while (wait) {
                    try {
                        waitForPlayer.wait();
                    } catch (InterruptedException e) {
                    	//TODO Fehlermeldung als Toast ausgeben
                    }
                }
            }
            action = choosenAbility;

        /*} else {
            action = currentPlayer.yourTurn(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());
        }*/
        return action;
    }


    /**
     * Call this on pause.
     */
    public void onPause() {
        synchronized (waitForPlayer) {
            wait = true;
        }
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
