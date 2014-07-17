/**
 * Author: Christian Roletscheck
 */

package fh.tagmon.gameengine.abilitys;

import java.io.Serializable;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;

abstract class AbilityComponent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2774505242350032072L;
	protected final AbilityComponentTypes componentType;
	protected final AbilityTargetRestriction componentTargetRestr;
	
	protected AbilityComponent(AbilityComponentTypes type, AbilityTargetRestriction componentTargetRestr){
		this.componentType = type;
		if(componentTargetRestr == null){
			this.componentTargetRestr = AbilityTargetRestriction.DEFAULT;
		}else{
			this.componentTargetRestr = componentTargetRestr;
		}
		
	}

	
}
