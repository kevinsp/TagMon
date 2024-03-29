/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.gameengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fh.tagmon.gameengine.player.PlayerInfo;


public class PlayerList {

    private LinkedList<PlayerListNode> playList = new LinkedList<PlayerListNode>();
    private HashMap<Integer, IHostPlayer> playerTargetList = new HashMap<Integer, IHostPlayer>();
    private List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();

    public List<PlayerInfo> getPlayerInfoList() {
		return playerInfoList;
	}

	private int playerCounter = 0;

    private PlayerListNode currentSelectedPlayerNode;
    private int currentPlayListPos = 0;

    private void selectNextPlayerNode() {
        this.currentSelectedPlayerNode = this.playList.get((currentPlayListPos % playList.size()));
        this.currentPlayListPos++;
    }

    public IHostPlayer getNextPlayer() {
        this.selectNextPlayerNode();
        return this.currentSelectedPlayerNode.getPlayer();
    }

    public int getCurrentPlayerTargetId() {
        return this.currentSelectedPlayerNode.getOwnTargetId();
    }

    public void addPlayer(IHostPlayer newPlayer) {
        PlayerListNode newNode = new PlayerListNode(newPlayer, this.playerCounter, 0);
        this.playList.add(newNode);
        this.playerTargetList.put(this.playerCounter, newPlayer);
        this.playerCounter++;
    }

    public HashMap<Integer, IHostPlayer> getPlayerTargetList() {
        return this.playerTargetList;
    }

    public IHostPlayer getPlayerByTargetId(int id) {
        return this.playerTargetList.get(id);
    }

	public LinkedList<PlayerListNode> getPlayList() {
		return playList;
	}
	
	public void addPlayerInfo(PlayerInfo playerInfoObj){
		this.playerInfoList.add(playerInfoObj);
	}
	
	public PlayerInfo getPlayerInfo(int id){
		return this.playerInfoList.get(id);
	}
	

    
}
