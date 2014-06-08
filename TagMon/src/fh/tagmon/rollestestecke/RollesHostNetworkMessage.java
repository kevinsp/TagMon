package fh.tagmon.rollestestecke;

abstract class RollesHostNetworkMessage {

	protected final RollesHostNetworkMessageTypes messageType;
	
	protected RollesHostNetworkMessage(RollesHostNetworkMessageTypes messageType){
		this.messageType = messageType;
	}
	
	
}
