package fh.tagmon.rollestestecke;



public class RollesHostGameOverMessage extends RollesHostNetworkMessage implements IHostNetworkMessage{
	
	public RollesHostGameOverMessage(){
		super(RollesHostNetworkMessageTypes.GAME_OVER);

	}

	@Override
	public RollesHostNetworkMessageTypes getMessageType() {
		return super.messageType;
	}
}
