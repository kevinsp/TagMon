package fh.tagmon.network.hostConnection;

import fh.tagmon.network.message.MessageObject;

public interface IHostConnection {

	public boolean sendMsgToClient(MessageObject<?> msgToClient);
	public MessageObject<?> reciveMsgFromClient();
	public void closeCon();
	
}
