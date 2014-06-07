package fh.tagmon.model;

import fh.tagmon.gameengine.abilitys.IDurationAbilityComponent;


public class DurationAbilityListElement implements Comparable<DurationAbilityListElement> {
	private int duration;
	private IDurationAbilityComponent durationAbilityComponent;
	private int id;
	
	protected DurationAbilityListElement(int duration, int id, IDurationAbilityComponent durationAbilityComponent){
		this.duration = duration;
		this.durationAbilityComponent = durationAbilityComponent;
		this.id = id;
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	public IDurationAbilityComponent getDurationAbilityComponent(){
		return this.durationAbilityComponent;
	}

	@Override
	public int compareTo(DurationAbilityListElement another) {
		int anotherDuration = another.getDuration();
		
		return this.duration - anotherDuration;
	}
	
	protected int decreaseDuration(){
		this.duration -= 1;
		return this.getDuration();
	}
	
	public int getId(){
		return this.id;
	}
}
