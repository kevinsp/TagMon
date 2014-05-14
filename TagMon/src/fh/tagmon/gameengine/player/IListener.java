package fh.tagmon.gameengine.player;

import java.util.HashMap;

public interface IListener {

	public void newRound(HashMap<Integer, IPlayer> targetList, int yourTargetId);
}
