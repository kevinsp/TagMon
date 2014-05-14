package fh.tagmon.gameengine.player;

import java.util.HashMap;

import fh.tagmon.gameengine.choseability.AbilityPreporator;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.deal_with_incoming_abilitys.AbilityComponentDirector;

public class MonsterPlayModule {

	private Monster myMonster;
	private AbilityComponentDirector myMonstersAbilityComponentDirector;
	private AbilityPreporator myMonstersAbilityPreporator;
	private EventManager eventManager;
	
	
	public MonsterPlayModule(Monster myNewMonster, EventManager eventManager){
		this.eventManager = eventManager;
		this.myMonster = myNewMonster;
		this.myMonstersAbilityComponentDirector = new AbilityComponentDirector(myNewMonster);
		this.myMonstersAbilityPreporator = new AbilityPreporator(myNewMonster);
		this.addNewRoundListeners();
	}
	
	
	private void addNewRoundListeners(){
		this.eventManager.addListener(myMonstersAbilityPreporator);
		this.eventManager.addListener(myMonstersAbilityComponentDirector);
	}
	
	
	public void handleAbilityComponents(IAbilityComponent abilityComponent){
		this.myMonstersAbilityComponentDirector.handleAbilityComponent(abilityComponent);
	}
	
	
	public String getLatestLogEntry(){
		return this.myMonstersAbilityComponentDirector.getLatestLog();
	}
	
	public AbilityPreporator getPrep(){
		return this.myMonstersAbilityPreporator;
	}
	
}
