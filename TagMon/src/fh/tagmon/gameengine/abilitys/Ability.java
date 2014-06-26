package fh.tagmon.gameengine.abilitys;

import java.io.Serializable;
import java.util.LinkedList;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class Ability implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1873594577447779429L;

	private int id;
	
	private String abilityName;
	private AbilityTargetRestriction targetRestriction;
	private int energyCost;
	private int cooldown;
	
	private LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();
	
	
	public Ability(int id, String abilityName, int energyCost, int cooldown, AbilityTargetRestriction targetRestriction){
		this.id = id;
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.cooldown = cooldown;
		this.energyCost = energyCost;	
	}
	
	public Ability(int id, String abilityName, int energyCost,int cooldown, AbilityTargetRestriction targetRestriction, LinkedList<IAbilityComponent> abilityComponents){
		this.id = id;
		this.targetRestriction = targetRestriction;
		this.abilityName = abilityName;
		this.energyCost = energyCost;
		this.cooldown = cooldown;
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
	
	
	public int getCooldown(){
		return cooldown;
	}
	
	public void setCooldown(int cooldown){
		this.cooldown = cooldown;
	}
	
}
