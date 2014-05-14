package fh.tagmon.testPrepareAbility;

import fh.tagmon.gameengine.choseability.AbilityPreporator;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;


import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import android.util.Log;

public class RunPrepareAbilityTest {

	
	public void run(){
		Monster newMonster = new Monster("Red",1,6,1,1);
		Damage dmgObj = new Damage(1);
		Ability abil = new Ability("test",2,AbilityTargetRestriction.SELF);
		abil.addAbilityComponent(dmgObj);
		newMonster.addAbility(abil);
		AbilityPreporator choser = new AbilityPreporator(newMonster);

		Log.i("prepTest", "BEFORE");
		for(IAbilityComponent comp: abil.getAbilityComponents()){
			Damage temp = (Damage) comp;
			String conv = String.valueOf(temp.getDamage());
			Log.i("prepTest", conv);
		}
		
		choser.getAbility(0);
		
		Log.i("prepTest", "After");
		for(IAbilityComponent comp: abil.getAbilityComponents()){
			Damage temp = (Damage) comp;
			String conv = String.valueOf(temp.getDamage());
			Log.i("prepTest", conv);
		}
	}
	
}
