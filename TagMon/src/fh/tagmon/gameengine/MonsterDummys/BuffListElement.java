package fh.tagmon.gameengine.MonsterDummys;

import fh.tagmon.gameengine.abilitys.Buff;


public class BuffListElement implements Comparable<BuffListElement> {
	private int duration;
	private Buff buffDeBuff;
	private int id;
	
	protected BuffListElement(int duration, int id, Buff buffDeBuff){
		this.duration = duration;
		this.buffDeBuff = buffDeBuff;
		this.id = id;
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	public Buff getBuff(){
		return this.buffDeBuff;
	}

	@Override
	public int compareTo(BuffListElement another) {
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
