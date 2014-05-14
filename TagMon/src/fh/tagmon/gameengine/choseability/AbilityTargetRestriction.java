package fh.tagmon.gameengine.choseability;

import java.util.LinkedList;

public enum AbilityTargetRestriction {

	DEFAULT(new LinkedList<Integer>()),
	ENEMY(new LinkedList<Integer>()),
	SELF(new LinkedList<Integer>());

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
