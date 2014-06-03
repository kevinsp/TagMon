package fh.tagmon.client;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.EventManager;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.guiParts.Fight;
import fh.tagmon.network.HostMessageObject;
import fh.tagmon.network.HostMessageObjectType;
import fh.tagmon.network.INetworkSender;
import fh.tagmon.network.NetworkConnection;
import android.content.Context;

public class GameClientEngine {

	private Context context;
	private boolean running = true;
	
	//GAMEPLAY
	private MonsterPlayModule monster;
	
	//NETWORK
	private NetworkConnection connection;
	
	public GameClientEngine(Context context, MonsterPlayModule mPM, NetworkConnection connection){
		this.context = context;
		this.connection = connection;
		monster = mPM;
	}
	
	public void run(){
		//TODO horche ob der host eine Playerlist schickt...
		//TODO klären mit Pascal
		((Fight) context).initBattleGUI(playerList.getPlayList(), 0); // player id is '0' for testing

		while(running){
			HostMessageObject hostMsg = connection.listenToBroadcast();
			switch(hostMsg.getType()){
			case ABILITY_COMPONENT:
				AnswerObject answerObject = monster.getMyMonstersAbilityComponentDirector().
						handleAbilityComponent(hostMsg.getAbilityComponent());
				connection.sendAnswerToHost(answerObject);
				break;
			case ENEMY_TURN_LOG:
				//TODO Wenn eine Kampfstatistik kommt, zeige sie zeitlich begrenzt an
				break;
			case YOUR_TURN_ORDER:
				//TODO Wenn eine Aufforderung kommt mach deinen Zug
				//Aufforderung an GUI weiterleiten und als Antwort ActionObject erhalten
				ActionObject actionObject = null;
				connection.sendActionToHost(actionObject);
				break;
			case GAME_OVER:
				stop(); 
				break;
			default:
				//TODO evtl Meldung auf Screen ausgeben
				break;
			
			}
			//Wenn Spiel zu Ende dann Client stoppen
		}
		//Statistik ausgeben und zum Hauptbildschirm zurück.
		//Optional: höchster Damage, gespielte Runden bla bla mitloggen..
	}
	
	private void stop(){
		running = false;
	}
}
