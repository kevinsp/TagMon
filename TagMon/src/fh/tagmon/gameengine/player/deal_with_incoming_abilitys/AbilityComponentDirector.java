package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;

import java.util.HashMap;
import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.EventManager;
import fh.tagmon.gameengine.player.IListener;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.model.BuffListElement;
import fh.tagmon.model.Monster;

public class AbilityComponentDirector implements IListener{

	
	//private Monster monster;
	private BuffHandler BuffHandler;
	private DamageHandler dmgHandler;
	private AbilityComponentLogger compLogger = new AbilityComponentLogger();
	
	
	public AbilityComponentDirector(Monster monster){
		//this.monster = monster;
		this.BuffHandler = new BuffHandler(monster,compLogger);
		this.dmgHandler = new DamageHandler(monster,compLogger);

	}
	
	
	public AnswerObject handleAbilityComponent(IAbilityComponent abilityComponent){

		switch(abilityComponent.getComponentType()){
		case BUFF:
			Buff buff_debuff = (Buff) abilityComponent;
			this.BuffHandler.handleBuff(buff_debuff);
			break;
		case DAMAGE:
			Damage dmgObj = (Damage) abilityComponent;
			this.dmgHandler.handleDamage(dmgObj);
			break;
		default:
			break;
		
		}
			
		//TODO answer zusammenbauen
		return null;
	}
	
	public String getLatestLog(){
		return this.compLogger.getLatestLogMsg();
	}

	@Override
	public void newRound(HashMap<Integer, IPlayer> targetList, int yourTargetId) {

		this.compLogger.newRound();
		this.BuffHandler.newRound();
		
	}
	
}
