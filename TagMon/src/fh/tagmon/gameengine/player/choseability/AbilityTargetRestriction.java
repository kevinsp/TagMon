/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player.choseability;

import java.util.LinkedList;

public enum AbilityTargetRestriction {
	// targetRestriction 1=self, 2=enemy, 3=selfANDenemy, 4=enemygroup, 5=owngroup, 6=selfANDenemygroup, 7=owngroupANDenemy
	DEFAULT(new LinkedList<Integer>()),
	SELF(new LinkedList<Integer>()),
	ENEMY(new LinkedList<Integer>()),
	SELFANDENEMY(new LinkedList<Integer>()),
	ENEMYGROUP(new LinkedList<Integer>()),
	OWNGROUP(new LinkedList<Integer>()),
	SELFANDENEMYGROUP(new LinkedList<Integer>()),
	OWNGROUPANDENEMY(new LinkedList<Integer>());

	private LinkedList<Integer> targetList;
	
	private AbilityTargetRestriction(LinkedList<Integer> targetList){
		this.targetList = targetList;
	}
	
	public void addTarget(int targetId){
		this.targetList.add(targetId);
	}
	
	public LinkedList<Integer> getTargetList(){
		return this.targetList;
	}
	
	public void cleanTargetList(){
		this.targetList.clear();
	}
	
	public void addMoreTargets(LinkedList<Integer> moreTargets){
		this.targetList.addAll(moreTargets);
	}
}
