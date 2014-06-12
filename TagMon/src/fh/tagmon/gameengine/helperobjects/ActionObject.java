package fh.tagmon.gameengine.helperobjects;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;


public class ActionObject {
	
	
	private final Ability abilityComp;
	private final AbilityTargetRestriction targetRes;
	
	public ActionObject (Ability ability, AbilityTargetRestriction targetRes){
		
		this.abilityComp = ability;
		this.targetRes = targetRes;

	}
	
	public AbilityTargetRestriction getTargetRestriction(){
		return this.targetRes;
	}
	
	public Ability getAbility(){
		return this.abilityComp;
	}

	
}