package fh.tagmon.gameengine.testDmgAbsorbationHandler;

import java.util.LinkedList;

import android.util.Log;
import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.model.BuffListElement;
import fh.tagmon.model.BuffListHandler;
import fh.tagmon.model.DamageAbsorbationHandler;

public class RunTestDmgAbsorbationHandler {

	public void run(){
		Monster monster = new Monster("Test Monster", 30, 1, 2, 1);
		int dmgLow = 2;
		int dmgHigh = 5;
		Buff buffOneRound = getDefenseAbility(1, 3);
		Buff buffThreeRound = getDefenseAbility(3, 10);
		
		for(int i = 0; i < 12; i++){
			myLogger("+++++++++++++++++++ NewRound +++++++++++++++++++");
			BuffListHandler buffListHandler = monster.getBuffListHandler();
			DamageAbsorbationHandler dmgAbsHand = monster.getDmgAbsHandler();
			if( i % 3 == 0 ){
//				Buff buffOneRound = getDefenseAbility(1, 3);
//				Buff buffThreeRound = getDefenseAbility(3, 10);
				buffListHandler.addBuff(buffOneRound);
				buffListHandler.addBuff(buffThreeRound);
			}
			myLogger("Before:");
			myLogger("DmgAbsorb Befor: " + String.valueOf(dmgAbsHand.getDamageAbsorbationAmount()));
			//BuffInfo(buffListHandler.getBuffList());
			
			int DmgAfterAbsorb = dmgAbsHand.doAbsorbation(dmgHigh);
			
			myLogger("After:");
			myLogger("DmgAbsorb After: " + String.valueOf(dmgAbsHand.getDamageAbsorbationAmount()));
			myLogger("BUFFLIST: ");
			BuffInfo(buffListHandler.getBuffList());
			buffListHandler.newRound();
		}
		
		
		
	}
	
	private void myLogger(String logstring){
		Log.i("DmgAbsTest", logstring);
	}
	
	private void BuffInfo(LinkedList<BuffListElement> buffList){
		if(buffList.size() > 0){
			int dmgAbsCount = 0;
			for(BuffListElement element: buffList){
				String buffId = String.valueOf(element.getId());
				String buffDuration = String.valueOf(element.getDuration());
				int dmgAbsValue = element.getBuff().getDamageAbsorbationAmount();
				dmgAbsCount += dmgAbsValue;
				String buffAbsorb = String.valueOf(dmgAbsValue);
				myLogger( "BuffID: |"+ buffId+"| BuffDuration: |" + buffDuration +"|"  + " BuffAbsorb: |" + buffAbsorb + "|");
			}
			//myLogger( "All BuffAbsorb (counted) : |" + String.valueOf(dmgAbsCount) + "|");
		}
		else{
			myLogger("BuffList is empty");
		}
	}

	
	private Buff getDefenseAbility(int buffTime, int dmgAbsorbAmount){
		Buff comp = new Buff(buffTime,0,0,0,dmgAbsorbAmount);
		return (comp);
	}
	
	private Damage getOffensiveAbility(int dmgAmount){
		Damage comp = new Damage(dmgAmount);
		return (comp);
	}
}
