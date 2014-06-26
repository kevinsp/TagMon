package fh.tagmon.gameengine.helperobjects;

import java.io.Serializable;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;


public class ActionObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8210956891399262445L;
	private final Ability ability;
	private final AbilityTargetRestriction targetRes;
	
	public ActionObject (Ability ability, AbilityTargetRestriction targetRes){
		
		this.ability = ability;
		this.targetRes = targetRes;

	}
	
	public AbilityTargetRestriction getTargetRestriction(){
		return this.targetRes;
	}
	
	public Ability getAbility(){
		return this.ability;
	}

	
}