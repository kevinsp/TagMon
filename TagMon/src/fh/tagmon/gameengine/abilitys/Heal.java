/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Heal extends AbilityComponent implements IAbilityComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3714041805347185546L;
	private int healValue;
	
	public Heal(int healValue, AbilityTargetRestriction abilityTargetRestriction){
		super(AbilityComponentTypes.HEAL, abilityTargetRestriction);
		this.healValue = healValue;
		
	}

	public int getHealAmount(){
		return this.healValue;
	}
	
	
	@Override
	public AbilityComponentTypes getComponentType() {
		return this.componentType;
	}

	@Override
	public void setReqStats(Monster monster) {
		//TODO evtl abhnging von stats machen
	}

	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}


}
