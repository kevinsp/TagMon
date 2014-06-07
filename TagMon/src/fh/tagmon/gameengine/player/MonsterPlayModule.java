package fh.tagmon.gameengine.player;


import java.util.HashMap;

import fh.tagmon.gameengine.player.choseability.AbilityChooser;
import fh.tagmon.gameengine.player.choseability.AbilityUpdater;
import fh.tagmon.gameengine.player.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.model.Monster;

public class MonsterPlayModule {

	private Monster myMonster;
	private AbilityComponentDirector myMonstersAbilityComponentDirector;
	private AbilityChooser abilityChooser;
	private EventManager internalEventManager;
	private AbilityUpdater gamePlayAbilityUpdater;
	
	
	public MonsterPlayModule(Monster myNewMonster){
		this.internalEventManager = new EventManager();
		this.myMonster = myNewMonster;
		this.myMonstersAbilityComponentDirector = new AbilityComponentDirector(myNewMonster);
		this.abilityChooser = new AbilityChooser(myNewMonster);
		this.gamePlayAbilityUpdater = new AbilityUpdater(myNewMonster);
		this.addNewRoundListeners();
	}
	
	
	public AbilityChooser getAbilityChooser() {
		return abilityChooser;
	}

	private void addNewRoundListeners(){
		this.internalEventManager.addListener(gamePlayAbilityUpdater);
		this.internalEventManager.addListener(myMonstersAbilityComponentDirector);
	}
	
	public void newRound(HashMap<Integer, IPlayer> targetList, int yourTargetId){
		this.internalEventManager.sendNewRoundEvent(targetList, yourTargetId);
	}
	
	public String getLatestLogEntry(){
		return this.myMonstersAbilityComponentDirector.getLatestLog();
	}
	
	
	public AbilityComponentDirector getMyMonstersAbilityComponentDirector() {
		return myMonstersAbilityComponentDirector;
	}
	
	public Monster getMonster(){
		return this.myMonster;
	}
}
