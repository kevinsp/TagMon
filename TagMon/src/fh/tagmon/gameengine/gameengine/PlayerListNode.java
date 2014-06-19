package fh.tagmon.gameengine.gameengine;

public class PlayerListNode {

	private IHostPlayer player;
	private int ownTargetId;
	private int ownGroupId;
	
	public PlayerListNode(IHostPlayer player, int ownTargetId, int ownGroupId){
		this.setPlayer(player);
		this.setOwnGroupId(ownTargetId);
		this.setOwnTargetId(ownTargetId);
	}

	public IHostPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IHostPlayer player) {
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
