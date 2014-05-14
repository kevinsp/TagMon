package fh.tagmon.gameengine.gameengine;

import java.util.LinkedList;
import java.util.Map;




import android.util.Log;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;


public class GamePlayEngine {

	PlayerList playerList;
	IPlayer currentPlayer;
	int currentRoundStatus = 0;
	int roundCounter = 0;
	boolean runGame = true;
	
	public GamePlayEngine(PlayerList newPList){
		this.playerList = newPList;
	}	
	
	public int run(){
		while(runGame){
			this.roundCounter++;
			// 1Phase Neuer Spieler
			currentPlayer = this.playerList.getNextPlayer();
			
			
			
			// 2Phase Spieler soll seinen Zugmachen
			ActionObject action = currentPlayer.yourTurn(this.playerList.getPlayerTargetList(),  this.playerList.getCurrentPlayerTargetId());
			
			myLogger("##################### ROUND: " + String.valueOf(this.roundCounter));
			myLogger("CurrentPlayer: " + currentPlayer.getPlayerName());
			String targetName = this.playerList.getPlayerByTargetId(action.getTargetRestriction().getTargetList().getFirst()).getPlayerName();
			myLogger("Chosen Ability: |" + action.getAbility().getAbilityName() + "| on Target: " + targetName );
			
			// 3phase Ability Zerlegen und Komponenten an den Richtigen Schicken
			this.sendAbilityComponentToPlayer(action);	
		}
		
		return 0;
	}
	
	private void myLogger(String toLog){
		Log.i("GameEngine", toLog);
	}
	
	
	private void sendAbilityComponentToPlayer(ActionObject action){
		Ability ability = action.getAbility();
		for(IAbilityComponent aComponent: ability.getAbilityComponents()){
			LinkedList<Integer> targetList = null;
			
			switch(aComponent.getComponentTargetRestriction()){
				case DEFAULT:
					AbilityTargetRestriction abiTarRes = action.getTargetRestriction();
					targetList = abiTarRes.getTargetList();
					break;
				case ENEMY:
				case SELF:
					targetList = aComponent.getComponentTargetRestriction().getTargetList();
					break;
				default:
					break;
			}
			
			this.sendToList(aComponent, targetList);
		}
	
	}
	
	private void sendToList(IAbilityComponent aComponent, LinkedList<Integer> targetList){
		for(Integer targetId: targetList){
			IPlayer player = this.playerList.getPlayerByTargetId(targetId);
			AnswerObject answer = player.workWithAbilityComponent(aComponent);
			
			myLogger("==== Answer from Player: " + player.getPlayerName() + " ====");
			myLogger(answer.getMsg());
			myLogger("====");
			/////////////////////////////////////////// TESTHALBER
			if(answer.isMonsterDead()){
				this.runGame = false;
			}
			//////////////////////////////
		}
	}
	
	
}
