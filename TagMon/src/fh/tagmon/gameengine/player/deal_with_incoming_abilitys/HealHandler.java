package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;

import fh.tagmon.gameengine.abilitys.Heal;
import fh.tagmon.model.Monster;

public class HealHandler {

	private Monster myMonster;
	private AbilityComponentLogger compLogger;
	
	public HealHandler(Monster monster, AbilityComponentLogger compLogger){
		
		this.myMonster = monster;
		this.compLogger = compLogger;
	}
	
	
	public int handleHeal(Heal heal){
		
		int healAmount = heal.getHealAmount();
		
		myMonster.increaseLifePoints(healAmount);
		
		String logString = "[HealHandler] monster was healed : |" + String.valueOf(healAmount) + "| .";
		logString += "Monster currentLife: " + this.myMonster.getCurrentLifePoints()+ ".";
		compLogger.addLogMsg(logString);
		
		return healAmount;
	}
	
	
}
