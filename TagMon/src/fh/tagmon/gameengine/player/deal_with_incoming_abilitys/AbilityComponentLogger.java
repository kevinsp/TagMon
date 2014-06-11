package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;

import java.util.HashMap;
import java.util.LinkedList;

public class AbilityComponentLogger {

	private final HashMap<Integer, LinkedList<String>> roundLog = new HashMap<Integer, LinkedList<String>>();
	private int currentRound = 0; // dass die runde bei 0 beginnt
	
	public AbilityComponentLogger(){
		this.createFreshEntry();
	}
	
	public void newRound(){
		this.currentRound += 1;
		this.createFreshEntry();
	}
	
	private void createFreshEntry(){
		LinkedList<String> stringList = new LinkedList<String>();
		this.roundLog.put(this.currentRound, stringList);
	}
	
	public void addLogMsg(String msg){
		this.roundLog.get(this.currentRound).addLast(msg);
	}
	
	public String getLatestLogMsg(){
		String retString = "Nothing Happend";
		if(this.roundLog.get(currentRound).size() > 0){
			retString = this.roundLog.get(currentRound).getLast();
		}
		return retString;
	}
	
	
}
