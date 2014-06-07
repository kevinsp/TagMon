package fh.tagmon.rollestestecke;

abstract class RollesHostNetworkMessage {

	private final RollesHostNetworkMessageTypes messageType;
	
	protected RollesHostNetworkMessage(RollesHostNetworkMessageTypes messageType){
		this.messageType = messageType;
	}
	
	public RollesHostNetworkMessageTypes getMessageType(){
		return this.messageType;
	}
	
	
}
