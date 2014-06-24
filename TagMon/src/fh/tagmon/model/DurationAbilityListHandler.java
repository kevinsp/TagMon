package fh.tagmon.model;

import java.util.Collections;
import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.IDurationAbilityComponent;

public class DurationAbilityListHandler {

	private LinkedList<DurationAbilityListElement> durationAbilityList = new LinkedList<DurationAbilityListElement>();
	private int idCounter = 0;
	
	public LinkedList<DurationAbilityListElement> getDurationAbilityList(){
		return this.durationAbilityList;
	}
	
 	private void refreshBuffDuration(){
		for(DurationAbilityListElement buffListElement : this.durationAbilityList){
			buffListElement.decreaseDuration();
		}
	}
	
	private void removeExpiredBuffs(){
		LinkedList<DurationAbilityListElement> elementsToRemove = new LinkedList<DurationAbilityListElement>();
		
		for(DurationAbilityListElement buffListElement : this.durationAbilityList){
			if(buffListElement.getDuration() <= 0)
				elementsToRemove.add(buffListElement);
		}
		
		for(DurationAbilityListElement buffListElement : elementsToRemove){
			durationAbilityList.remove(buffListElement);
		}
	}
	
	public void newRound(){
		this.refreshBuffDuration();
		this.removeExpiredBuffs();
		Collections.sort(this.durationAbilityList);
	}
	
	public void addDurationAbilityListElement(IDurationAbilityComponent abilityComponent){
		// clone deswegen da ich local ohne netzwerk teste und es sonst zu problemen kommt
		// da das buff object ja nur einmal exestiert. Sobald ich was daran endere ist es immer geändert. Daher lieber mit dem Clone arbbeiten
		// so funktoniert auch der dmgAbsorbHandler
		this.durationAbilityList.add(new DurationAbilityListElement(abilityComponent.getDuration(),this.idCounter,abilityComponent)); 
		this.idCounter++;
	}
	
	
}
