package fh.tagmon.gameengine.gameengine;

import java.io.Serializable;

public class PlayerInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4385124210688914488L;
	private String playerName;
    private int currentLife;
    private int maxLife;
    private int id;
	
	public PlayerInfo(String playerName){
		this.playerName = playerName;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}

    public int getCurrentLife() {
        return currentLife;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getId() {
        return id;
    }
}
