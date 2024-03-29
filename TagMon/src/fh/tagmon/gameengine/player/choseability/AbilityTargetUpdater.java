/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player.choseability;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import android.util.Log;
import fh.tagmon.gameengine.player.PlayerInfo;



public class AbilityTargetUpdater{

	protected void prepareAllTargetRestrictions(List<PlayerInfo> targetList, int yourTargetId){
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
				case ENEMYGROUP:
				case OWNGROUP:
				case OWNGROUPANDENEMY:
				case SELFANDENEMY:
				case SELFANDENEMYGROUP:
				default:
					break;
			
			}		
		}
		
	}
		
 	private void prepareEnemy(List<PlayerInfo> targetList, int yourTargetId, AbilityTargetRestriction ablTarRes){
		LinkedList<Integer> enemyTargetIds = new LinkedList<Integer>();
		
		for(PlayerInfo entry: targetList){
			int targetId = entry.ID;
			if(targetId != yourTargetId){
				enemyTargetIds.add(targetId);
			}
		}
		ablTarRes.addMoreTargets(enemyTargetIds);
	}

}
