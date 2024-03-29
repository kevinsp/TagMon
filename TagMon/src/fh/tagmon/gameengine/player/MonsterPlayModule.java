/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player;


import java.util.HashMap;
import java.util.List;


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
		// Klassen die wissen mssen ob eine neue Runde beginnt
		this.addNewRoundListeners();
	}
	
	
	public AbilityChooser getAbilityChooser() {
		return abilityChooser;
	}

	private void addNewRoundListeners(){
		this.internalEventManager.addListener(gamePlayAbilityUpdater);
		this.internalEventManager.addListener(myMonstersAbilityComponentDirector);
	}
	
	public void newRound(List<PlayerInfo> targetList, int yourTargetId){
		this.internalEventManager.sendNewRoundEvent(targetList, yourTargetId);
	}
	
	public String getLatestLogEntry(){
		return this.myMonstersAbilityComponentDirector.getLatestLog();
	}
	
	
	public AbilityComponentDirector getMonstersAbilityComponentDirector() {
		return myMonstersAbilityComponentDirector;
	}
	
	public Monster getMonster(){
		return this.myMonster;
	}
}
