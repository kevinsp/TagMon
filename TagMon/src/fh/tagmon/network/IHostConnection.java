package fh.tagmon.network;

import fh.tagmon.network.message.MessageObject;

public interface IHostConnection {

	public boolean sendMsgToClient(MessageObject<?> msgToClient);
	public MessageObject<?> reciveMsgFromClient();
	
}
