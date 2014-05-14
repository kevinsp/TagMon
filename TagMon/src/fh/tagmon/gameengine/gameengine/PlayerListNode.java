package fh.tagmon.gameengine.gameengine;

import fh.tagmon.gameengine.player.IPlayer;

public class PlayerListNode {

	private IPlayer player;
	private int ownTargetId;
	private int ownGroupId;
	
	public PlayerListNode(IPlayer player, int ownTargetId, int ownGroupId){
		this.setPlayer(player);
		this.setOwnGroupId(ownTargetId);
		this.setOwnTargetId(ownTargetId);
	}

	public IPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	public int getOwnTargetId() {
		return ownTargetId;
	}

	public void setOwnTargetId(int ownTargetId) {
		this.ownTargetId = ownTargetId;
	}

	public int getOwnGroupId() {
		return ownGroupId;
	}

	public void setOwnGroupId(int ownGroupId) {
		this.ownGroupId = ownGroupId;
	}
	
	
}
