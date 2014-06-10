package fh.tagmon.gameengine.player;

import java.util.HashMap;

import fh.tagmon.gameengine.gameengine.PlayerInfo;

public interface IListener {

	public void newRound(HashMap<Integer, PlayerInfo> targetList, int yourTargetId);
}
