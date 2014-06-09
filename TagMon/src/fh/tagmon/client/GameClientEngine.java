package fh.tagmon.client;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import fh.tagmon.gameengine.gameengine.PlayerList;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.guiParts.Fight;
import fh.tagmon.guiParts.ISetAbility;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.network.HostMessageObject;
import fh.tagmon.network.NetworkConnection;
import fh.tagmon.network.NetworkSocketConnection;

public class GameClientEngine implements Observer, ISetAbility{
	
	private Context context;
    private final Object waitForPlayer;
    private boolean wait;
    private ActionObject choosenAbility;
	
	//GAMEPLAY
	private MonsterPlayModule monster;
	
	//NETWORK
	private NetworkConnection connection;
	
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
			//AnswerObject answerObject = monster.getMyMonstersAbilityComponentDirector().handleAbilityComponent(((HostMessageObject) hostMsg).getAbilityComponent());
			//connection.sendAnswerToHost(answerObject);
			break;
		case ENEMY_TURN_LOG:
			//TODO Wenn eine Kampfstatistik kommt, zeige sie zeitlich begrenzt an
            //TODO  DialogBuilder(Context context,String title, String text, int timeTilClose)
			break;
		case GAME_OVER:
			stop(); 
			//Statistik ausgeben und zum Hauptbildschirm zurck.
			//Optional: hchster Damage, gespielte Runden bla bla mitloggen..
			break;
		case YOUR_TURN_ORDER:
			//TODO Wenn eine Aufforderung kommt mach deinen Zug
			//Aufforderung an GUI weiterleiten und als Antwort ActionObject erhalten
			PlayerList playerList = ((HostMessageObject) hostMsg).getPlayerList(); // WARUM GREIFT DIE GUI AUF DIE SPIELER LISTE DES HOSTS ZU???
            //TODO nicht jedes mal die gui initialisieren -> UPDATEN
			((Fight) context).initBattleGUI(playerList.getPlayList(), 0); // player id is '0' for testing
			ActionObject actionObject = waitForAction(playerList);
			connection.sendActionToHost(actionObject);
			break;
		default:
			//TODO evtl Meldung auf Screen ausgeben
			break;
		}
	}
	
	private ActionObject waitForAction(PlayerList playerList) {
        ActionObject action = null;


      //  if (currentPlayer.getId() == 0) {
            onPause();

            ((Fight) context).chooseAbility(playerList.getPlayerTargetList(), playerList.getCurrentPlayerTargetId(), this);

            //currentPlayer.sendNewRoundEvent(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());

            synchronized (waitForPlayer) {
                while (wait) {
                    try {
                        waitForPlayer.wait();
                    } catch (InterruptedException e) {
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
