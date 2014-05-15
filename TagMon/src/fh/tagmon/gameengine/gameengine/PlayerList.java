package fh.tagmon.gameengine.gameengine;

import java.util.HashMap;
import java.util.LinkedList;

import fh.tagmon.gameengine.player.IPlayer;


public class PlayerList {

    private LinkedList<PlayerListNode> playList = new LinkedList<PlayerListNode>();
    private HashMap<Integer, IPlayer> playerTargetList = new HashMap<Integer, IPlayer>();

    private int playerCounter = 0;

    private PlayerListNode currentSelectedPlayerNode;
    private int currentPlayListPos = 0;

    private void selectNextPlayerNode() {
        this.currentSelectedPlayerNode = this.playList.get((currentPlayListPos % playList.size()));
        this.currentPlayListPos++;
    }

    public IPlayer getNextPlayer() {
        this.selectNextPlayerNode();
        return this.currentSelectedPlayerNode.getPlayer();
    }

    public int getCurrentPlayerTargetId() {
        return this.currentSelectedPlayerNode.getOwnTargetId();
    }

    public void addPlayer(IPlayer newPlayer) {
        PlayerListNode newNode = new PlayerListNode(newPlayer, this.playerCounter, 0);
        this.playList.add(newNode);
        this.playerTargetList.put(this.playerCounter, newPlayer);
        this.playerCounter++;
    }

    public HashMap<Integer, IPlayer> getPlayerTargetList() {
        return this.playerTargetList;
    }

    public IPlayer getPlayerByTargetId(int id) {
        return this.playerTargetList.get(id);
    }

    public LinkedList getPlayList() {
        return playList;
    }
}
