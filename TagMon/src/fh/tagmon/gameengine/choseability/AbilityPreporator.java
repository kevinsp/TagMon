package fh.tagmon.gameengine.choseability;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.player.IListener;
import fh.tagmon.gameengine.player.IPlayer;

public class AbilityPreporator implements IListener{

	private Monster myMonster;
	private final HashMap<Integer, Ability> monsterAbilitys = new HashMap<Integer, Ability>();
	private int abilityCounter = 0;
	
	public AbilityPreporator(Monster newMonster){
		this.myMonster = newMonster;
		this.generateHashMap(newMonster.getAbilitys());
	}
		
	public Ability getAbility(int pos){
		Ability abi = this.monsterAbilitys.get(pos);
		return abi;
	}
	
	
	private void generateHashMap(LinkedList<Ability> list){
		for(Ability abil: list){
			this.monsterAbilitys.put(abilityCounter, abil);
			this.abilityCounter++;
		}
	}
	
	private void prepAbilityWithStats(Ability plainAbility){	
		plainAbility.setReqStats(this.myMonster);
	}

	private void prepareAllAbilitys(){
		for(Entry<Integer,Ability> entry: this.monsterAbilitys.entrySet()){
			this.prepAbilityWithStats(entry.getValue());
		}
	}
	
	private void prepareAllTargetRestrictions(HashMap<Integer, IPlayer> targetList, int yourTargetId){
		EnumSet<AbilityTargetRestriction> allAbilityTargetRestriction = EnumSet.allOf(AbilityTargetRestriction.class);
		
		for(AbilityTargetRestriction ablTarRes: allAbilityTargetRestriction){
			ablTarRes.cleanTargetList();
			switch (ablTarRes){
			case DEFAULT:
				break;
			case ENEMY:
				this.prepareEnemy(targetList, yourTargetId, ablTarRes);
				break;
			case SELF:
				ablTarRes.addTarget(yourTargetId);
				break;
			default:
				break;
			
			}		
		}
	}
	
 	private void prepareEnemy(HashMap<Integer, IPlayer> targetList, int yourTargetId, AbilityTargetRestriction ablTarRes){
		LinkedList<Integer> enemyTargetIds = new LinkedList<Integer>();
		for(Entry<Integer,IPlayer> entry: targetList.entrySet()){
			int targetId = entry.getKey();
			if(targetId != yourTargetId){
				enemyTargetIds.add(targetId);
			}
		}
		ablTarRes.addMoreTargets(enemyTargetIds);
	}

	
	
	@Override
	public void newRound(HashMap<Integer, IPlayer> targetList, int yourTargetId) {
		this.prepareAllTargetRestrictions(targetList, yourTargetId);
		this.prepareAllAbilitys();
	}


}
