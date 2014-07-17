/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player;

import java.util.HashMap;
import java.util.List;



public interface IListener {

	public void newRound(List<PlayerInfo> targetList, int yourTargetId);
}
