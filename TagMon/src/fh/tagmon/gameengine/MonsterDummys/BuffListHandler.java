package fh.tagmon.gameengine.MonsterDummys;

import java.util.Collections;
import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Buff;

public class BuffListHandler {

	private LinkedList<BuffListElement> buffList = new LinkedList<BuffListElement>();
	private int idCounter = 0;
	
	public LinkedList<BuffListElement> getBuffList(){
		return this.buffList;
	}
	
 	private void refreshBuffDuration(){
		for(BuffListElement buffListElement : this.buffList){
			buffListElement.decreaseDuration();
		}
	}
	
	private void removeExpiredBuffs(){
		LinkedList<BuffListElement> elementsToRemove = new LinkedList<BuffListElement>();
		
		for(BuffListElement buffListElement : this.buffList){
			if(buffListElement.getDuration() <= 0)
				elementsToRemove.add(buffListElement);
		}
		
		for(BuffListElement buffListElement : elementsToRemove){
			buffList.remove(buffListElement);
		}
	}
	
	public void newRound(){
		this.refreshBuffDuration();
		this.removeExpiredBuffs();
		Collections.sort(this.buffList);
	}
	
	public void addBuff(Buff buff){
		this.buffList.add(new BuffListElement(buff.getDuration(),this.idCounter,buff));
		this.idCounter++;
	}
	
	
}
