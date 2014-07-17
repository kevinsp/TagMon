/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Stun extends AbilityComponent implements IAbilityComponent{
	private static final long serialVersionUID = 1L;
	private int stunDuration;

	public Stun(int stunDuration,AbilityTargetRestriction componentTargetRestr) {
		super(AbilityComponentTypes.STUN, componentTargetRestr);
		this.stunDuration = stunDuration;
	}

	public int getStunDuration(){
		return this.stunDuration;
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
