package fh.tagmon.rollestestecke;

public class RollesClientGameStartMessage extends RollesClientNetworkMessage implements RollesIClientNetworkMessage{

	private String playerName;

	
	public RollesClientGameStartMessage(String playerName){
		super(RollesClientNetworkMessageTypes.GAME_START);
		this.playerName = playerName;

	}
	
	public String getPlayerName(){
		return this.playerName;
	}
	
	@Override
	public RollesClientNetworkMessageTypes getMessageType() {
		return super.messageType;
	}

}
