package fh.tagmon.gameengine.abilitys;

import java.io.Serializable;
import java.util.LinkedList;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Ability implements Serializable {
	private int id;
	private static final long serialVersionUID = 1L;
	private String abilityName;
	private AbilityTargetRestriction targetRestriction;
	private int energyCost;
	
	private LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();
	
	
	public Ability(int id, String abilityName, int energyCost, AbilityTargetRestriction targetRestriction){
		this.id = id;
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;	
	}
	
	public Ability(int id, String abilityName, int energyCost, AbilityTargetRestriction targetRestriction, LinkedList<IAbilityComponent> abilityComponents){
		this.id = id;
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;
		this.abilityComponents = abilityComponents;
	}
	
	public boolean addAbilityComponent(IAbilityComponent abilityComp){
		return this.abilityComponents.add(abilityComp);
	}
	
	public int getID(){
		return this.id;
	}
	

	public LinkedList<IAbilityComponent> getAbilityComponents(){
		return this.abilityComponents;
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
	
	public int getEnergyCosts(){
		return energyCost;
	}
	
}
