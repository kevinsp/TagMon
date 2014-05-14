package fh.tagmon.gameengine.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventManager {

	private final List<IListener> listener = new ArrayList<IListener>();
	
	public void addListener(IListener lis){
		this.listener.add(lis);
	}
	
	public void sendNewRoundEvent(HashMap<Integer, IPlayer> targetList, int yourTargetId){
		for(IListener ls: this.listener){
			ls.newRound(targetList, yourTargetId);
		}
	}
}
