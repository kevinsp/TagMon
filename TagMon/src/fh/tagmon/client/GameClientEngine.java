package fh.tagmon.client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import fh.tagmon.gameengine.gameengine.PlayerList;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.guiParts.Fight;
import fh.tagmon.guiParts.GuiPartsToUpdate;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.HostMessageObject;
import fh.tagmon.network.NetworkConnection;
import fh.tagmon.network.NetworkSocketConnection;
import android.content.Context;
import android.util.Log;

public class GameClientEngine implements Observer{
	
	private Context context;
	
	//GAMEPLAY
	private MonsterPlayModule monster;
	
	//NETWORK
	private NetworkConnection connection;
	
	public GameClientEngine(Context context, MonsterPlayModule mPM, ConnectionType type){
		this.context = context;
		this.monster = mPM;
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
		switch(((HostMessageObject) hostMsg).getType()){
		case ABILITY_COMPONENT:
			AnswerObject answerObject = monster.getMyMonstersAbilityComponentDirector().
				handleAbilityComponent(((HostMessageObject) hostMsg).getAbilityComponent());
			connection.sendAnswerToHost(answerObject);
			break;
		case ENEMY_TURN_LOG:
			//TODO Wenn eine Kampfstatistik kommt, zeige sie zeitlich begrenzt an
			break;
		case GAME_OVER:
			stop(); 
			//Statistik ausgeben und zum Hauptbildschirm zurück.
			//Optional: höchster Damage, gespielte Runden bla bla mitloggen..
			break;
		case YOUR_TURN_ORDER:
			//TODO Wenn eine Aufforderung kommt mach deinen Zug
			//Aufforderung an GUI weiterleiten und als Antwort ActionObject erhalten
			PlayerList playerList = ((HostMessageObject) hostMsg).getPlayerList();
			((Fight) context).initBattleGUI(playerList.getPlayList(), 0); // player id is '0' for testing
			ActionObject actionObject = null;
			connection.sendActionToHost(actionObject);
			break;
		default:
			//TODO evtl Meldung auf Screen ausgeben
			break;
		}
	}
	
	private ActionObject waitForAction() {
        ActionObject action = null;


        if (currentPlayer.getId() == 0) {
            onPause();

            ((Fight) context).chooseAbility(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId(), this);
            currentPlayer.sendNewRoundEvent(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());

            synchronized (waitForPlayer) {
                while (wait) {
                    try {
                        waitForPlayer.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            action = actionFromUser;
        } else {
            action = currentPlayer.yourTurn(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());
        }
        return action;
    }
}
