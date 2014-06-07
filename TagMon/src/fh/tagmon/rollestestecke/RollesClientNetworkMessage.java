package fh.tagmon.rollestestecke;


abstract class RollesClientNetworkMessage {

	private final RollesClientNetworkMessageTypes messageType;
	
	protected RollesClientNetworkMessage(RollesClientNetworkMessageTypes messageType){
		this.messageType = messageType;
	}
	
	public RollesClientNetworkMessageTypes getMessageType(){
		return this.messageType;
	}
	
}
