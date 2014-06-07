package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.abilitys.Ability;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;

public class RollesClientActionMessage extends RollesClientNetworkMessage {

	private final Ability abilityComp;
	private final AbilityTargetRestriction targetRes;
	
	protected RollesClientActionMessage(Ability ability, AbilityTargetRestriction targetRes){
		super(RollesClientNetworkMessageTypes.ACTION);
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
