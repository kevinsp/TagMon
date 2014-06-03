package fh.tagmon.gameengine.player;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.player.choseability.AbilityPreporator;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.model.Monster;

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
	
	
	
	public String getLatestLogEntry(){
		return this.myMonstersAbilityComponentDirector.getLatestLog();
	}
	
	public AbilityPreporator getPrep(){
		return this.myMonstersAbilityPreporator;
	}
	
	public AbilityComponentDirector getMyMonstersAbilityComponentDirector() {
		return myMonstersAbilityComponentDirector;
	}
	
}
