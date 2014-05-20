package fh.tagmon.gameengine.abilitys;

import java.util.LinkedList;
import java.util.ListIterator;




import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;

public class Ability {
	
	private String abilityName;
	private int energyCost;
	private LinkedList<AbilityTargetRestriction> targetRestriction = new LinkedList<AbilityTargetRestriction> ();
	
	private LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();
	
	public Ability(String abilityName, int energyCost, LinkedList<AbilityTargetRestriction> targetRestriction){
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;	
	}
	
	public Ability(String abilityName, int energyCost, AbilityTargetRestriction targetRestriction){
		this.targetRestriction.add(targetRestriction);
		this.abilityName = abilityName;
		this.energyCost = energyCost;	
	}	
	
	public boolean addAbilityComponent(IAbilityComponent abilityComp){
		return this.abilityComponents.add(abilityComp);
	}
	
	public LinkedList<IAbilityComponent> getAbilityComponents(){
		return (LinkedList<IAbilityComponent>) this.abilityComponents.clone();
	}
	
	public String getAbilityName(){
		return this.abilityName;
	}
	
	public void setReqStats(Monster monster){
		for(IAbilityComponent component: this.abilityComponents){
			component.setReqStats(monster);
		}
	}

	public LinkedList<AbilityTargetRestriction> getTargetRestriction() {
		return targetRestriction;
	}

}
