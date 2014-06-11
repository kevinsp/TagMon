package fh.tagmon.network;

import fh.tagmon.network.message.MessageObject;


public interface IClientConnection {

	public MessageObject<?> listenToBroadcast();
	public void sendToHost(MessageObject<?> msg);
	public void closeConnection();
}
