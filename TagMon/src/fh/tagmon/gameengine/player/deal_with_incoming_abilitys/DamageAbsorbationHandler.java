/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;

import fh.tagmon.gameengine.abilitys.Schadensabsorbation;
import fh.tagmon.model.DamageAbsorbationHelper;
import fh.tagmon.model.Monster;

public class DamageAbsorbationHandler {
	
	private DamageAbsorbationHelper dmgAbsorbHelper;
	private AbilityComponentLogger componentLogger;

	public DamageAbsorbationHandler(Monster monster, AbilityComponentLogger componentLogger){
		this.dmgAbsorbHelper = monster.getDmgAbsHelper();
		this.componentLogger = componentLogger;
	}
	
	private String prepForLog(String stringi, int nr){
		String retStr = "|"+stringi+": " + String.valueOf(nr) + "|";
		return retStr;
	}
	
	public void handleDamageAbsorbation(Schadensabsorbation schadensAbs){
		this.dmgAbsorbHelper.addDamageAbsorbationComponant(schadensAbs);
		logSchadenAbsorb();
	}
	
	private void logSchadenAbsorb(){
		int dmgAbsAmount = this.dmgAbsorbHelper.getDamageAbsorbationAmount();
		if( dmgAbsAmount != 0 ){
			String logMsg = "[DamageAbsorbHandler] monster has: " + prepForLog("DmgAbs", dmgAbsAmount) +".";
			this.componentLogger.addLogMsg(logMsg);
		}
	}
}
