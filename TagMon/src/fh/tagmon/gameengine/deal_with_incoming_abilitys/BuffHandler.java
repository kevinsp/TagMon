package fh.tagmon.gameengine.deal_with_incoming_abilitys;


import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.model.BuffListElement;
import fh.tagmon.model.BuffListHandler;
import fh.tagmon.model.Monster;


public class BuffHandler{
	
	
	private Monster monster;
	private BuffListHandler buffListHandler;
	private AbilityComponentLogger componentLogger;

	
	
	public BuffHandler(Monster monster, AbilityComponentLogger componentLogger){
		this.monster = monster;
		this.componentLogger = componentLogger;
		this.buffListHandler = monster.getBuffListHandler();
	}
	
	
	public void handleBuff(Buff newBuff){
		this.buffListHandler.addBuff(newBuff);
		this.refreshBuffEffects();
	}

	public void refreshBuffEffects() {
		LinkedList<BuffListElement> activBuffList = this.scanForActiveBuffs();
//		if(activBuffList.size() > 0){
			this.putActivBuffsIntoEffect(activBuffList);
//		}
	}
	
	private void putActivBuffsIntoEffect(LinkedList<BuffListElement> activBuffList){
		int strengthBuff = 0;
		int armorValueBuff = 0;
		int constitutionBuff = 0;
		
		for(BuffListElement buffListElement: activBuffList){
			Buff theBuff = buffListElement.getBuff(); 
			strengthBuff += theBuff.getStrengthBuff();
			armorValueBuff += theBuff.getArmorValueBuff();
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
		
		int dmgAbsorg = this.monster.getDmgAbsHandler().getDamageAbsorbationAmount();
		
		if( dmgAbsorg != 0 ){
			stringToLog += this.prepForLog("DMG_ABSORB", dmgAbsorg);
		}
		
		this.componentLogger.addLogMsg(stringToLog);
	}
	
	private String prepForLog(String stringi, int nr){
		String retStr = "|"+stringi+": " + String.valueOf(nr) + "|";
		return retStr;
	}
	
	private LinkedList<BuffListElement> scanForActiveBuffs(){
		LinkedList<BuffListElement> activBuffList = new LinkedList<BuffListElement>();
		
		for(BuffListElement buffListElement: this.buffListHandler.getBuffList()){
			Buff theBuff = buffListElement.getBuff(); 
			
			int strengthBuff = theBuff.getStrengthBuff();
			int armorValueBuff = theBuff.getArmorValueBuff();
			int constitutionBuff = theBuff.getConstitutionBuff();
			

			
			if( strengthBuff > 0 || armorValueBuff > 0 || constitutionBuff > 0 ){
				activBuffList.add(buffListElement);
			}
		}
		return activBuffList;
	}
	
	public void newRound() {
		this.buffListHandler.newRound();
		this.refreshBuffEffects();
	}
}





