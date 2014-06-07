package fh.tagmon.rollestestecke;

import java.util.HashMap;

import fh.tagmon.gameengine.player.IPlayer;

public class RollesHostYourTurnMessage extends RollesHostNetworkMessage implements IHostNetworkMessage {

	private final HashMap<Integer, IPlayer> targetList;
	private final int yourTargetId;
	
	public RollesHostYourTurnMessage(HashMap<Integer, IPlayer> targetList, int yourTargetId){
		super(RollesHostNetworkMessageTypes.YOUR_TURN);
		this.targetList = targetList;
		this.yourTargetId = yourTargetId;
	}

	public HashMap<Integer, IPlayer> getTargetList() {
		return targetList;
	}

	public int getYourTargetId() {
		return yourTargetId;
	}
}
