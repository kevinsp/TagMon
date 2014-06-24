package fh.tagmon.network.message;

import java.io.Serializable;


public final class MessageObject <T extends Object> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6815233209174409685L;
	public final MessageType messageType;
	public final int senderID;
	private final T content;
	
	
	
	MessageObject(MessageType type, T content, int senderID) {
		this.messageType = type;
		this.content = content;
		this.senderID = senderID;
	}

	public boolean isHostMessage(){
		return messageType.isHostMessageType();
	}
	
	public T getContent(){
		return content;
	}
}
