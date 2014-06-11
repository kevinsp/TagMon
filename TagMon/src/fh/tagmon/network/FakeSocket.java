package fh.tagmon.network;

import fh.tagmon.network.message.MessageObject;


public class FakeSocket implements IClientConnection, IHostConnection{
	
	private volatile MessageObject<?>  msgFromHost = null;
	private volatile MessageObject<?> msgFromClient = null;
	private final boolean HOST = true;
	private final boolean CLIENT = false;
	

	public MessageObject<?> popMsgFromHost(){
		return popMsgOf(HOST);
	}
	
	public MessageObject<?> popMsgFromClient(){
		return popMsgOf(CLIENT);
	}
	
	private MessageObject<?> popMsgOf(boolean host){
		MessageObject<?> msg = null;
		if(host){
			msg = msgFromHost;
			msgFromHost = null;
		}else{
			msg = msgFromClient;
			msgFromClient = null;
		}
		return msg;
	}
	
	@Override
	public void sendToHost(MessageObject<?> msg) {
		sendMsgFrom(CLIENT, msg);
	}
	
	@Override
	public boolean sendMsgToClient(MessageObject<?> msg){
		sendMsgFrom(HOST, msg);
		return true;
	}
	
	private void sendMsgFrom(boolean host, MessageObject<?> msg){
		if(host) msgFromHost = msg;
		else 	 msgFromClient = msg;
	}
	
	@Override
	public MessageObject<?> reciveMsgFromClient() {
		return receiveMsg(CLIENT);
	}

	@Override
	public MessageObject<?> listenToBroadcast() {
		return receiveMsg(HOST);
	}

	private MessageObject<?> receiveMsg(boolean host){
		while(true){
			MessageObject<?> msg = host ?
					this.popMsgFromHost():
					this.popMsgFromClient();
			if( msg != null){
				return msg;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closeConnection() {}
	
}
