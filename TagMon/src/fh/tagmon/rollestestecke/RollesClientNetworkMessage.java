package fh.tagmon.rollestestecke;


abstract class RollesClientNetworkMessage {

	protected final RollesClientNetworkMessageTypes messageType;
	
	protected RollesClientNetworkMessage(RollesClientNetworkMessageTypes messageType){
		this.messageType = messageType;
	}
	

	
}
