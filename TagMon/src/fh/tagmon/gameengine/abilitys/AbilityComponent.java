package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.MonsterDummys.Monster;



abstract class AbilityComponent {
	protected final AbilityComponentTypes componentType;
	protected final AbilityTargetRestriction componentTargetRestr;
	
	protected AbilityComponent(AbilityComponentTypes type, AbilityTargetRestriction componentTargetRestr){
		this.componentType = type;
		this.componentTargetRestr = componentTargetRestr;
	}
	
	
	protected AbilityComponent(AbilityComponentTypes type){
		this.componentType = type;
		this.componentTargetRestr = AbilityTargetRestriction.DEFAULT;
	}
	
}
