package fh.tagmon.gameengine.player.choseability;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import fh.tagmon.gameengine.player.IPlayer;


public class AbilityTargetUpdater{

	protected void prepareAllTargetRestrictions(HashMap<Integer, IPlayer> targetList, int yourTargetId){
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

}
