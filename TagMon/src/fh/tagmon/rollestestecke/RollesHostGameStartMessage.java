package fh.tagmon.rollestestecke;

public class RollesHostGameStartMessage extends RollesHostNetworkMessage implements IHostNetworkMessage{
	
	private final int yourTargetId;
	
	public RollesHostGameStartMessage(int playerTargetId){
		super(RollesHostNetworkMessageTypes.GAME_START);
		this.yourTargetId = playerTargetId;
	}

	public int getYourTargetId() {
		return yourTargetId;
	}

	@Override
	public RollesHostNetworkMessageTypes getMessageType() {
		return super.messageType;
	}
}
