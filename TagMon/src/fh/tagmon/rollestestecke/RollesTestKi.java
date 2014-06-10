package fh.tagmon.rollestestecke;

import java.util.Random;

import android.util.Log;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class RollesTestKi {

    private String kiName;
    private MonsterPlayModule playModule;
    private int id = 0;
    private ClientMsgPreparer myConnector;
    
    public RollesTestKi(String name, Monster myMonster, ClientMsgPreparer connector) {
        this.kiName = name;
        this.myConnector = connector;

        this.playModule = new MonsterPlayModule(myMonster);
    }   
    
    private int myRandomWithHigh(int low, int high) {
        // Zufallszahlen inklusive Obergrenze
        high++;
        return (int) (Math.random() * (high - low) + low);
    }
    
    
    private Ability choseRandomAbility() {
        int maxAbil = playModule.getAbilityChooser().getAllAbilitys().keySet().size() - 1; //da von 0 an gezählt
    	int random = this.myRandomWithHigh(0, maxAbil);
        
    	return this.playModule.getAbilityChooser().getAbility(random);
    }

    private AbilityTargetRestriction choseRandomTarget(Ability chosenAbility) {
    	
    	AbilityTargetRestriction targetRest = chosenAbility.getTargetRestriction();
    	int targetRestListSize = targetRest.getTargetList().size();
    	Random rand = new Random();
    	int randomTargetId = targetRest.getTargetList().get(rand.nextInt(targetRestListSize));
    	targetRest.cleanTargetList();
    	targetRest.addTarget(randomTargetId);
    	return targetRest;
    }
 
    
    public void playTheGame(){
    	boolean gameIsNotOver = true;
    	while(gameIsNotOver){
    		HostNetworkMessage msgFromHost = this.myConnector.waitForMsgFromHost();
    		
    		switch(msgFromHost.getMessageType()){
			case DEAL_WITH_INCOMING_ABILITY_COMPONENT:
				doDealWith(msgFromHost);
				break;
			case GAME_OVER:
				gameIsNotOver = false;
				break;
			case GAME_START:
				this.id = msgFromHost.getPlayersId();
				this.myConnector.sendGameStartsMsg(this.kiName);
				break;
			case YOUR_TURN:
				doMyTurn(msgFromHost);
				break;
			default:
				break;
    		
    		}
    		
    		
    	}
    		
    }
    
    private void doMyTurn(HostNetworkMessage yourTurnMsg){
    	//ULTRA WICHTIG muss jedes mal befor ich drann bin ausgeführt werden
    	this.playModule.newRound(yourTurnMsg.getTargetList(), this.id); 
    	
    	Ability chosenAbility = choseRandomAbility();
    	AbilityTargetRestriction targetRes = this.choseRandomTarget(chosenAbility);
    	
    	ActionObject myAction = new ActionObject(chosenAbility,targetRes);
    	this.myConnector.sendActionMsg(myAction);
    	
    }
    
    private void doDealWith(HostNetworkMessage dealWithMsg){
    	IAbilityComponent abilityCompToDealWith = dealWithMsg.getAbilityComponent();
    	
    	this.playModule.getMonstersAbilityComponentDirector().handleAbilityComponent(abilityCompToDealWith);
    	
    	String retMsg = this.playModule.getLatestLogEntry();
    	boolean isMyMonsterDead = false;
    	if(this.playModule.getMonster().getCurrentLifePoints() <= 0){
    		isMyMonsterDead = true;
    	}
    	AnswerObject answer = new AnswerObject(retMsg, isMyMonsterDead);
    	
    	this.myConnector.sendAnswerMsg(answer);
    	
    	
    }
    
    
}
