package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Stun extends AbilityComponent implements IAbilityComponent{

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
		//TODO evtl abh�nging von stats machen
	}

	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}

	
}