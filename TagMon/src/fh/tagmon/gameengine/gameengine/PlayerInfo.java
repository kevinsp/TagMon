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
}
