package fh.tagmon.gameengine.gameengine;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

import fh.tagmon.gameengine.abilitys.AbilityComponentTypes;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.Schadensabsorbation;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.helperobjects.SummaryObject;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;

public class GameHostEngine extends AsyncTask<Void, Void, Void>{

    private PlayerList playerList;
    private IHostPlayer currentPlayer;
    private int roundCounter = 0;
    private boolean runGame = true;
    private String logTag = "GameEngine";


    public GameHostEngine(PlayerList newPList) {
        this.playerList = newPList;
    }

	@Override
	protected Void doInBackground(Void... params) {
		go();
		return null;
	}

    private void initGameStart(){
    	for(Entry<Integer, IHostPlayer> entry : this.playerList.getPlayerTargetList().entrySet()) {
    		PlayerInfo playerInfo = entry.getValue().gameStarts(entry.getKey());
    		this.playerList.addPlayerInfo(playerInfo);
    	}

        SummaryObject summary = SummaryObject.getInstance();
        summary.addPlayerInfoList(this.playerList.getPlayerInfoList());
        broadcastSummary(summary);
    }
    
    private void gameOver(int targetId){
    	String playerWhoLost = this.playerList.getPlayerInfo(targetId).NAME;
    	myLogger("Player is dead " + playerWhoLost);
    	for(Entry<Integer, IHostPlayer> entry : this.playerList.getPlayerTargetList().entrySet()) {
    		entry.getValue().gameOver(playerWhoLost);
    	}
    	
    	myLogger("GAME_OVER");
    	this.runGame = false;
    }
    

    public void go() {
    	initGameStart();
       
    	while (runGame) {
            this.roundCounter++;
            myLogger("##################### ROUND: " + String.valueOf(this.roundCounter));
            
            // 1Phase Neuer Spieler
            currentPlayer = this.playerList.getNextPlayer();

            // 2Phase
            ActionObject action = currentPlayer.yourTurn(this.playerList.getPlayerInfoList(), this.playerList.getCurrentPlayerTargetId());

            
           
            myLogger("CurrentPlayer: " + this.playerList.getPlayerInfo(this.playerList.getCurrentPlayerTargetId()).NAME);
           int test = action.getTargetRestriction().getTargetList().getFirst();
            String targetName = this.playerList.getPlayerInfo(test).NAME;
            myLogger("Chosen Ability: |" + action.getAbility().getAbilityName() + "| on Target: " + targetName);

            // 3phase Ability Zerlegen und Komponenten an den Richtigen Schicken
            this.sendAbilityComponentToPlayer(action);
        }

    }

    private void myLogger(String toLog) {
        Log.i(this.logTag, toLog);
    }

    private void testFunc(IAbilityComponent comp){
    	////TEST
    	if(comp.getComponentType() == AbilityComponentTypes.SCHADENSABSORBATION){
    		Schadensabsorbation sch = (Schadensabsorbation) comp;
    		Log.i("GameEngine", "Host Resv ... abs:" + String.valueOf(sch.getAbsorbationAmount()));
    	}
    	//// End
    }

    private void sendAbilityComponentToPlayer(ActionObject action) {

        List<AbilityComponentList> affectedPlayers = new ArrayList<AbilityComponentList>();
        for(PlayerListNode player : playerList.getPlayList())
        	affectedPlayers.add(new AbilityComponentList(player.getOwnTargetId()));
        
        HashMap<Integer,AbilityComponentList> idToAbilityCompListMap = new HashMap<Integer, AbilityComponentList>();
        
        //Get Ability-components
        for(IAbilityComponent component : action.getAbility().getAbilityComponents()){
        	//Get the targetIDs of the specific component
        	///TEST
        		//testFunc(component); // ob CloneMe weggelassen werden kann (Rolle)
        	/////
        	LinkedList<Integer> targetList = null;
        	switch (component.getComponentTargetRestriction()) {
	            case DEFAULT:
	                 AbilityTargetRestriction abiTarRes = action.getTargetRestriction();
	                 targetList = abiTarRes.getTargetList();
	                 break;
	            case ENEMY:
	            case SELF:
				case ENEMYGROUP:
				case OWNGROUP:
				case OWNGROUPANDENEMY:
				case SELFANDENEMY:
				case SELFANDENEMYGROUP:
					  targetList = component.getComponentTargetRestriction().getTargetList();
				default:
				        break;
        	}
        		
        	
        	for(Integer clientID : targetList){
        		if(idToAbilityCompListMap.containsKey(clientID)){
        			AbilityComponentList abComList = idToAbilityCompListMap.get(clientID);
        			abComList.addAbilityCommponent(component);
        		}
        		else{
        			AbilityComponentList abComList = new AbilityComponentList(clientID);
        			abComList.addAbilityCommponent(component);
        			idToAbilityCompListMap.put(clientID, abComList);
        		}
        	}
        }
        
        SummaryObject summary = sendComponentListsToPlayersAndReceiveTheirAnswers(idToAbilityCompListMap);
        if(summary != null)
        	broadcastSummary(summary);
        
      
    }

    private SummaryObject sendComponentListsToPlayersAndReceiveTheirAnswers(HashMap<Integer,AbilityComponentList> affectedPlayers){
    	SummaryObject summary = SummaryObject.getInstance();
        boolean monsterIsDead = false;
    	for(Integer targetId : affectedPlayers.keySet()){
    		AnswerObject answer = sendComponentListToPlayer(affectedPlayers.get(targetId));
    		if (answer.isMonsterDead()) {
    			this.gameOver(targetId);
    			monsterIsDead = true;
    		}
    			
    		summary.add(answer);
        }
    	if(monsterIsDead){
    		return null;
    	}
        return summary;
    }
    
    private AnswerObject sendComponentListToPlayer(AbilityComponentList al){
    	myLogger("==== Answer from Player: " + this.playerList.getPlayerInfo(al.target).NAME + " ====");
    	final IHostPlayer player = this.playerList.getPlayerByTargetId(al.target);
    	AnswerObject answer  = player.dealWithAbilityComponents(al);
    	
        myLogger(answer.getMsg());
        
		/////////////////////////////////////////// TESTHALBER
		
		//////////////////////////////
        return answer;
    }

    private void broadcastSummary(SummaryObject summary){
    	for(PlayerListNode player : playerList.getPlayList())
        	player.getPlayer().printSummary(summary);
    }
    
}
