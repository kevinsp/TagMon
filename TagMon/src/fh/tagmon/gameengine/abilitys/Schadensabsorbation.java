/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Schadensabsorbation extends AbilityComponent implements IAbilityComponent,IDurationAbilityComponent {
	
	
	int duration;
	int absorbationsAmount;
	
	public Schadensabsorbation(int duration, int absorbationsAmount,AbilityTargetRestriction componentTargetRestr) {
		super(AbilityComponentTypes.SCHADENSABSORBATION, componentTargetRestr);
		this.absorbationsAmount = absorbationsAmount;
		this.duration = duration;
	}

	//brauch man nicht mehr sobalt ber netzwerk die objecte transportiert werden
	@Override
	public Schadensabsorbation cloneMe(){
		return new Schadensabsorbation(this.duration, this.absorbationsAmount, this.componentTargetRestr);
	}
	
	public int getAbsorbationAmount(){
		return this.absorbationsAmount;
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
	
	public void setAbsorbationAmount(int newAbsorbationAmount){
		this.absorbationsAmount = newAbsorbationAmount;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}
}
