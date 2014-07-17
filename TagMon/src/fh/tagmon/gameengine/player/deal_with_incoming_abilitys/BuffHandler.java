/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;


import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.AbilityComponentTypes;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.IDurationAbilityComponent;
import fh.tagmon.model.DurationAbilityListElement;
import fh.tagmon.model.DurationAbilityListHandler;
import fh.tagmon.model.Monster;


public class BuffHandler{
	
	
	private Monster monster;
	private DurationAbilityListHandler durationAbilityListHandler;
	private AbilityComponentLogger componentLogger;

	
	
	public BuffHandler(Monster monster, AbilityComponentLogger componentLogger){
		this.monster = monster;
		this.componentLogger = componentLogger;
		this.durationAbilityListHandler = monster.getDurationAbilityListHandler();
	}
	
	
	public void handleBuff(Buff newBuff){
		this.durationAbilityListHandler.addDurationAbilityListElement(newBuff);
		this.refreshBuffEffects();
	}

	public void refreshBuffEffects() {
		LinkedList<Buff> activBuffList = this.scanForActiveBuffs();
		if(activBuffList.size() > 0){
			this.putActivBuffsIntoEffect(activBuffList);
		}
	}
	
	private void putActivBuffsIntoEffect(LinkedList<Buff> activBuffList){
		int strengthBuff = 0;
		int armorValueBuff = 0;
		int constitutionBuff = 0;
		
		for(Buff buff: activBuffList){
			Buff theBuff = buff;
			strengthBuff += theBuff.getStrengthBuff();
			constitutionBuff += theBuff.getConstitutionBuff();
		}
		
		this.monster.setAdditionalStrength(strengthBuff);
		this.monster.setAdditionalArmorValue(armorValueBuff);
		this.monster.setAdditionalConstitution(constitutionBuff);
		
		this.logActiveBuffEffect(strengthBuff, armorValueBuff, constitutionBuff);
	}
	
	private void logActiveBuffEffect(int strengthBuff,int  armorValueBuff,int constitutionBuff ){
		String stringToLog= "[BuffHandler] Monster is Buffed with following stats: ";
		
		if( strengthBuff != 0){
			stringToLog += this.prepForLog("STR", strengthBuff);
		}
		if( armorValueBuff != 0){
			stringToLog += this.prepForLog("ARMOR", armorValueBuff);
		}
		if( constitutionBuff != 0){
			stringToLog += this.prepForLog("CON", constitutionBuff);
		}
				
	
		
		this.componentLogger.addLogMsg(stringToLog);
	}
	
	private String prepForLog(String stringi, int nr){
		String retStr = "|"+stringi+": " + String.valueOf(nr) + "|";
		return retStr;
	}
	
	private LinkedList<Buff> scanForActiveBuffs(){
		LinkedList<Buff> activBuffList = new LinkedList<Buff>();
		
		for(DurationAbilityListElement durationAbilityListElement: this.durationAbilityListHandler.getDurationAbilityList()){
			IDurationAbilityComponent durationAbilityComponent = durationAbilityListElement.getDurationAbilityComponent();
			IAbilityComponent abilityComponent = (IAbilityComponent) durationAbilityComponent;
			
			if(abilityComponent.getComponentType() == AbilityComponentTypes.BUFF){
				Buff theBuff = (Buff) abilityComponent;
				int strengthBuff = theBuff.getStrengthBuff();
				int constitutionBuff = theBuff.getConstitutionBuff();

				if( strengthBuff > 0 || constitutionBuff > 0 ){
					activBuffList.add(theBuff);
				}
			}
			
			
		}
		return activBuffList;
	}
	
	public void newRound() {
		this.durationAbilityListHandler.newRound();
		this.refreshBuffEffects();
	}
}





