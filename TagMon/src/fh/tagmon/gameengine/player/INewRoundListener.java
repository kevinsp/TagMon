/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player;

import java.util.HashMap;

public interface INewRoundListener {

	public void newRound(HashMap<Integer, PlayerInfo> targetList, int yourTargetId);
}
