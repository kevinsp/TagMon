package fh.tagmon.rollestestecke;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import fh.tagmon.client.Helper_PlayerSettings;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.AbilityComponentTypes;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.Schadensabsorbation;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.model.Monster;
import fh.tagmon.network.clientConnections.NetworkSocketConnection;
import fh.tagmon.network.message.MessageFactory;
import fh.tagmon.network.message.MessageObject;

public class RollesTestKi {

    private String kiName;
    private MonsterPlayModule playModule;
    private NetworkSocketConnection connection;
    private int id = 0;
    private final String TAG = "KI";
    
    public RollesTestKi(String name, Monster myMonster) {
        this.kiName = name;
        try {
			this.connection = new NetworkSocketConnection("localhost");
		} catch (IOException e) {
			Log.e(TAG, "Unable to Connect to localhost!");
		}
        this.playModule = new MonsterPlayModule(myMonster);
    }   
    
    private int myRandomWithHigh(int low, int high) {
        // Zufallszahlen inklusive Obergrenze
        high++;
        return (int) (Math.random() * (high - low) + low);
    }
    
    
    private Ability choseRandomAbility() {
        int maxAbil = playModule.getAbilityChooser().getAllAbilitys().keySet().size() - 1; //da von 0 an gezhlt
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
 
    
    @SuppressWarnings("unchecked")
	public void playTheGame(){
    	boolean gameIsNotOver = true;
    	while(gameIsNotOver){
    		MessageObject<?> msgFromHost = this.connection.listenToBroadcast();
    		
    		switch(msgFromHost.messageType){
			case ABILITY_COMPONENT:
				doDealWith(msgFromHost);
				break;
			case GAME_OVER:
				gameIsNotOver = false;
				break;
			case GAME_START:
				this.id = (Integer)msgFromHost.getContent();
                PlayerInfo info = new PlayerInfo(kiName, id);
                info.setCurrentLife(playModule.getMonster().getCurrentLifePoints());
                info.setMaxLife(playModule.getMonster().getMaxLifePoints());
				this.connection.sendToHost(MessageFactory.createClientMessage_GameStart(info, id));
				break;
			case YOUR_TURN:
				doMyTurn((MessageObject<List<PlayerInfo>>)msgFromHost);
				break;
			default:
				break;
    		
    		}
    		
    		
    	}
    		
    }
    
    private void doMyTurn(MessageObject<List<PlayerInfo>> yourTurnMsg){
    	List<PlayerInfo> li = (List<PlayerInfo>) yourTurnMsg.getContent();
    	
    	this.playModule.newRound( (List<PlayerInfo>) yourTurnMsg.getContent(), this.id); 
    	
    	Ability chosenAbility = choseRandomAbility();
    	AbilityTargetRestriction targetRes = this.choseRandomTarget(chosenAbility);
    	
    	ActionObject myAction = new ActionObject(chosenAbility,targetRes);
    	
//    	////TEST
//    	IAbilityComponent comp = chosenAbility.getAbilityComponents().getFirst();
//    	if(comp.getComponentType() == AbilityComponentTypes.SCHADENSABSORBATION){
//    		Schadensabsorbation sch = (Schadensabsorbation) comp;
//    		Log.i("GameEngine", "KI sending ... abs:" + String.valueOf(sch.getAbsorbationAmount()));
//    	}
    	
    	
    	
    	//// End
    	this.connection.sendToHost(MessageFactory.createClientMessage_Action(myAction, id));
    	
    }
    
    private void doDealWith(MessageObject<?> dealWithMsg){
    	///CHRIS test
    	AbilityComponentDirector director = playModule.getMonstersAbilityComponentDirector();
		AbilityComponentList abilityComponents = (AbilityComponentList) dealWithMsg.getContent();
		PlayerInfo info = new PlayerInfo(kiName, id);
		AnswerObject answerObject = director.handleAbilityComponents(abilityComponents, info);
		
		
		if(this.playModule.getMonster().getCurrentLifePoints() <= 0){
			answerObject.setMonsterIsDead(true);
		}
		
		connection.sendToHost(MessageFactory.createClientMessage_Answer(answerObject, id));
    }
    
    
}
