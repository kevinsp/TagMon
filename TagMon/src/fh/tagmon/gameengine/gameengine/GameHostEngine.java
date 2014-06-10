package fh.tagmon.gameengine.gameengine;

import android.util.Log;

import java.util.LinkedList;
import java.util.Map.Entry;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;

public class GameHostEngine {

    private PlayerList playerList;
    private IHostPlayer currentPlayer;
    private int roundCounter = 0;
    private boolean runGame = true;
    private String logTag = "GameEngine";


    public GameHostEngine(PlayerList newPList) {
        this.playerList = newPList;
    }
    
    private void initGameStart(){
    	for(Entry<Integer, IHostPlayer> entry : this.playerList.getPlayerTargetList().entrySet()) {
    		PlayerInfo playerInfo = entry.getValue().gameStarts(entry.getKey());
    		this.playerList.addPlayerInfo(entry.getKey(), playerInfo);
    	}
    }
    
    private void gameOver(){
    	for(Entry<Integer, IHostPlayer> entry : this.playerList.getPlayerTargetList().entrySet()) {
    		entry.getValue().gameOver();
    	}
    	myLogger("GAME_OVER");
    	this.runGame = false;
    }
    
    //function for testing for rolle
    public void go() {
    	initGameStart();
       
    	while (runGame) {
            this.roundCounter++;
            // 1Phase Neuer Spieler
            currentPlayer = this.playerList.getNextPlayer();

            // 2Phase
            ActionObject action = currentPlayer.yourTurn(this.playerList.getPlayerInfoMap(), this.playerList.getCurrentPlayerTargetId());

            myLogger("##################### ROUND: " + String.valueOf(this.roundCounter));
           
            myLogger("CurrentPlayer: " + this.playerList.getPlayerInfo(this.playerList.getCurrentPlayerTargetId()).getPlayerName());
           
            String targetName = this.playerList.getPlayerInfo(action.getTargetRestriction().getTargetList().getFirst()).getPlayerName();
            myLogger("Chosen Ability: |" + action.getAbility().getAbilityName() + "| on Target: " + targetName);

            // 3phase Ability Zerlegen und Komponenten an den Richtigen Schicken
            this.sendAbilityComponentToPlayer(action);
        }

    }

    private void myLogger(String toLog) {
        Log.i(this.logTag, toLog);
    }


    private void sendAbilityComponentToPlayer(ActionObject action) {
        Ability ability = action.getAbility();
        for (IAbilityComponent aComponent : ability.getAbilityComponents()) {
            LinkedList<Integer> targetList = null;

            switch (aComponent.getComponentTargetRestriction()) {
                case DEFAULT:
                    AbilityTargetRestriction abiTarRes = action.getTargetRestriction();
                    targetList = abiTarRes.getTargetList();
                    break;
                case ENEMY:
                case SELF:
                    targetList = aComponent.getComponentTargetRestriction().getTargetList();
                    break;
				case ENEMYGROUP:
				case OWNGROUP:
				case OWNGROUPANDENEMY:
				case SELFANDENEMY:
				case SELFANDENEMYGROUP:
	            default:
	                    break;
            }

            this.sendToList(aComponent, targetList);
        }

    }

    private void sendToList(IAbilityComponent aComponent, LinkedList<Integer> targetList) {
        for (Integer targetId : targetList) {
            final IHostPlayer player = this.playerList.getPlayerByTargetId(targetId);
            AnswerObject answer = player.dealWithAbilityComponent(aComponent);

            myLogger("==== Answer from Player: " + this.playerList.getPlayerInfo(targetId).getPlayerName() + " ====");
            myLogger(answer.getMsg());
            myLogger("====");
            /////////////////////////////////////////// TESTHALBER
            if (answer.isMonsterDead()) {
                this.gameOver();
            	
            }
            //////////////////////////////
        }
    }
}
