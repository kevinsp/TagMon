package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;




public interface IAbilityComponent {

	public AbilityComponentTypes getComponentType();
	public void setReqStats(Monster monster);
	public AbilityTargetRestriction getComponentTargetRestriction();

}
