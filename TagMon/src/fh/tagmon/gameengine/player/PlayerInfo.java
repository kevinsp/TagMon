package fh.tagmon.gameengine.player;

import java.io.Serializable;

public class PlayerInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	public  String NAME;
    public int ID;
    private int currentLife;
    private int maxLife;
	
	public PlayerInfo(String playerName, int id){
		this.NAME = playerName;
		this.ID = id;
	}

	public void setCurrentLife(int curLife) {
		currentLife = curLife;
	}
    public int getCurrentLife() {
        return currentLife;
    }

    public void setMaxLife(int maxLife){
    	this.maxLife = maxLife;
    }
    public int getMaxLife() {
        return maxLife;
    }
}
