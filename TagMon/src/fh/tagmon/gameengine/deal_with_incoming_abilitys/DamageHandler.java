package fh.tagmon.gameengine.deal_with_incoming_abilitys;

import fh.tagmon.gameengine.MonsterDummys.DamageAbsorbationHandler;
import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Damage;

public class DamageHandler {

	private Monster monster;
	private DamageAbsorbationHandler dmgAbsHandler;
	private AbilityComponentLogger compLogger;
	
	public DamageHandler(Monster monster, AbilityComponentLogger compLogger){
		this.monster = monster;
		this.dmgAbsHandler = monster.getDmgAbsHandler();
		this.compLogger = compLogger;
	}
	
	public void handleDamage(Damage dmgObj){
		int dmg = dmgObj.getDamage();
		int armorValue = this.monster.getArmorValue();
		int dmgAfterAbsorb = this.dmgAbsHandler.doAbsorbation(dmg);
		int dmgDiff = dmg - dmgAfterAbsorb;
		
		String logMsg = "";
		
		if (dmgDiff > 0 ){
			logMsg += "Some Dmg was Absorbed. "+this.prepForLog(dmg)+" -> "+this.prepForLog(dmgAfterAbsorb)+" AbsorbAmount: "+ this.prepForLog(dmgDiff) +".";
		}
		
		if (dmgAfterAbsorb > 0 ){
			int efectiveDmg = dmgAfterAbsorb - armorValue;
			logMsg += "Dmg reduction "+this.prepForLog(dmgAfterAbsorb)+" -> "+this.prepForLog(efectiveDmg)+". ARMOR: "+ this.prepForLog(armorValue)+"."; 
			
			
			if(efectiveDmg > 0){
				this.monster.decreaseLifePoints(efectiveDmg);
				logMsg += "Dmg taken "+this.prepForLog(efectiveDmg)+"."; 
			}
			else{
				
			}
	
		}
		logMsg += "Monster currentLife: " + this.prepForLog(this.monster.getCurrentLifePoints())+ ".";
		this.logForMe(logMsg);
	}
	
	private String prepForLog(int nr){
		return "|" + String.valueOf(nr) + "|";
	}
	
	private void logForMe(String msg){
		this.compLogger.addLogMsg("[DamageHandler] " + msg);
	}
}
