package fh.tagmon.gameengine.testBuffhandler;

import java.util.LinkedList;

import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;

import android.util.Log;
import fh.tagmon.gameengine.MonsterDummys.BuffListElement;
import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.deal_with_incoming_abilitys.AbilityComponentDirector;
import fh.tagmon.gameengine.deal_with_incoming_abilitys.BuffHandler;


public class RunBuffHandlerTest {

	
	
	public void run(){
//		Monster newMonster = new Monster("Red",10,1,1,1);
//		
//		AbilityComponentDirector abilityComponentDirector = new AbilityComponentDirector(newMonster);
//		
//		Damage damageOne = new Damage(3);
//		Ability abilityTwo = new Ability("two",1,AbilityTargetRestriction.ENEMY);		
//		abilityTwo.addAbilityComponent(damageOne);
//		
//		LinkedList<BuffListElement> buffList = newMonster.getBuffListHandler().getBuffList();
//		
//		for(int i = 1; i < 12 ; i++){
//			Log.i("buffTest", "############# ROUND: " + String.valueOf(i));
//			if(i%2 == 0){
//				Buff buffOneRound = new Buff(1,5,5,5,4);
//				Buff buffTwoRound = new Buff(3,10,10,10,10);
//				Ability abilityOne = new Ability("one",1, AbilityTargetRestriction.ENEMY);
//				abilityOne.addAbilityComponent(buffOneRound);
//				abilityOne.addAbilityComponent(buffTwoRound);
//				this.handleAbility(abilityComponentDirector,abilityOne);
//			}
//			Log.i("buffTest", "BEFORE: ");
//			this.BuffInfo(buffList);
//			this.handleAbility(abilityComponentDirector,abilityTwo);
//			Log.i("buffTest", "AFTER: ");
//			this.BuffInfo(buffList);
//			Log.i("buffTest", "MONSTER Life: " + String.valueOf(newMonster.getCurrentLifePoints()));
//			Log.i("buffTest", "MONSTER DmgAbs: " + String.valueOf(newMonster.getDmgAbsHandler().getDamageAbsorbationAmount()));
//			abilityComponentDirector.newRound();
//		}
	}
		
	public void handleAbility(AbilityComponentDirector acd, Ability ability){
		for(IAbilityComponent component: ability.getAbilityComponents()){
			acd.handleAbilityComponent(component);
		}
	}
	
	private void BuffInfo(LinkedList<BuffListElement> buffList){
		for(BuffListElement element: buffList){
			String buffId = String.valueOf(element.getId());
			String buffDuration = String.valueOf(element.getDuration());
			String buffAbsorb = String.valueOf(element.getBuff().getDamageAbsorbationAmount());
			Log.i("buffTest", "BuffID: |"+ buffId+"| BuffDuration: |" + buffDuration +"|"  + "BuffAbsorb: | " + buffAbsorb );
		}
	}
		
		
		
		
	
	
}
