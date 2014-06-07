package fh.tagmon.rollestestecke;

public class RollesClientGameStartMessage extends RollesClientNetworkMessage{

	private String playerName;

	
	public RollesClientGameStartMessage(String playerName){
		super(RollesClientNetworkMessageTypes.GAME_START);
		this.playerName = playerName;

	}
	
	public String getPlayerName(){
		return this.playerName;
	}

}
