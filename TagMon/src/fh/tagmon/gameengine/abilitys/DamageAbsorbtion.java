/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class DamageAbsorbtion extends AbilityComponent implements IAbilityComponent, IDurationAbilityComponent {
	private static final long serialVersionUID = 1L;
	int duration;
	int absorbationsAmount;
	
	public DamageAbsorbtion(int duration, int absorbationsAmount,AbilityTargetRestriction componentTargetRestr) {
		super(AbilityComponentTypes.SCHADENSABSORBATION, componentTargetRestr);
		this.absorbationsAmount = absorbationsAmount;
		this.duration = duration;
	}
	
	public int getAbsorbationAmount(){
		return this.absorbationsAmount;
	}
	
	@Override
	public AbilityComponentTypes getComponentType() {
		return this.componentType;
	}

	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}
	
	public void setAbsorbationAmount(int newAbsorbationAmount){
		this.absorbationsAmount = newAbsorbationAmount;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}
	
	@Override
	public IDurationAbilityComponent clone(){
		DamageAbsorbtion clone = new DamageAbsorbtion(duration, absorbationsAmount, componentTargetRestr);
		return clone;
	}

	@Override
	public IDurationAbilityComponent cloneMe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReqStats(Monster monster) {
		// TODO Auto-generated method stub
		
	}
}
