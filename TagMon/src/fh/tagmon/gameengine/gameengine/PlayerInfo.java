package fh.tagmon.gameengine.gameengine;

public class PlayerInfo {

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
