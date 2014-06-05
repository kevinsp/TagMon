package fh.tagmon.gameengine.abilitys;

import java.util.LinkedList;
import java.util.ListIterator;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Ability {
	
	private String abilityName;
	private int energyCost;
	private AbilityTargetRestriction targetRestriction;
	
	private LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();
	
	
	public Ability(String abilityName, int energyCost, AbilityTargetRestriction targetRestriction){
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;	
	}
	
	public Ability(String abilityName, int energyCost, AbilityTargetRestriction targetRestriction, LinkedList<IAbilityComponent> abilityComponents){
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;
		this.abilityComponents = abilityComponents;
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

	public AbilityTargetRestriction getTargetRestriction() {
		return targetRestriction;
	}

}
