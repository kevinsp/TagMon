package fh.tagmon.gameengine.gameengine;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

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
    	/* TODO was ich mir hier vorstelle:
    	 * Hier sollte die Ability zerpflückt werden soweit klar
    	 * dann sollten die verschiedenen AbilityComponents gesammelt werden und zwar nach targets getrennt
    	 * dafür hab ich die AbilityComponentList gebaut, die kannst dafür dann verwendet.
    	 * dann geht für jeden Spieler genau eine Message raus, die alle AbilityComponents enthält, die ihn betreffen.
    	 */
    	
    	
    	
        
        List<AbilityComponentList> affectedPlayers = new ArrayList<AbilityComponentList>();
        
        for(IAbilityComponent component : action.getAbility().getAbilityComponents()){
        	for(Integer clientID : component.getComponentTargetRestriction().getTargetList()){
        		AbilityComponentList curList = null;
        		for(AbilityComponentList affectedPlayer : affectedPlayers)
        			if(affectedPlayer.target == clientID){
        				curList = affectedPlayer;
        				break;
        			}
        		if(curList == null){
        			curList = new AbilityComponentList(clientID);
        			affectedPlayers.add(curList);
        		}
        		curList.addAbilityCommponent(component);
        	}
        }
        
        String summary = sendComponentListsToPlayersAndReceiveTheirAnswers(affectedPlayers);
        broadcastSummary(summary);
        
        /*
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
		//*/
    }
    /* Auskommentiert von Chris
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
    */
    private void broadcastSummary(String summary){
    	for(PlayerListNode player : playerList.getPlayList())
        	player.getPlayer().printSummary(summary);
    }
    private String sendComponentListsToPlayersAndReceiveTheirAnswers(List<AbilityComponentList> affectedPlayers){
    	StringBuilder summary = new StringBuilder();
        for(AbilityComponentList al : affectedPlayers){
        	AnswerObject answer = sendComponentListToPlayer(al);
        	summary.append(answer.getMsg());
        }
        return summary.toString();
    }
    private AnswerObject sendComponentListToPlayer(AbilityComponentList al){
    	final IHostPlayer player = this.playerList.getPlayerByTargetId(al.target);
    	AnswerObject answer  = player.dealWithAbilityComponents(al);
    	myLogger("==== Answer from Player: " + this.playerList.getPlayerInfo(al.target).getPlayerName() + " ====");
        myLogger(answer.getMsg());
        myLogger("====");
        return answer;
    }
    
}
