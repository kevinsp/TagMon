package fh.tagmon.rollestestecke;


public class FakeSocket implements IClientConnection, IHostConnection{
	
	private volatile IHostNetworkMessage  msgFromHost = null;
	private volatile IClientNetworkMessage msgFromClient = null;
	

	public IHostNetworkMessage popMsgFromHost(){
		IHostNetworkMessage retStr = msgFromHost;
		msgFromHost = null;
		return retStr;
	}
	
	public IClientNetworkMessage popMsgFromClient(){
		IClientNetworkMessage retStr = msgFromClient;
		msgFromClient = null;
		return retStr;
	}
	
	@Override
	public boolean sendMsgToHost(IClientNetworkMessage msgToHost) {
		this.msgFromClient = msgToHost;
		return true;
	}
	
	@Override
	public boolean sendMsgToClient(IHostNetworkMessage msg){
		this.msgFromHost = msg;
		return true;
	}
	

	@Override
	public IClientNetworkMessage reciveMsgFromClient() {
		while(true){
			IClientNetworkMessage msgFromClient = this.popMsgFromClient();
			if( msgFromClient != null){
				return msgFromClient;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	@Override
	public IHostNetworkMessage reciveMsgFromHost() {
		while(true){

			IHostNetworkMessage msgFromHost = this.popMsgFromHost();
			if( msgFromHost != null){
				return msgFromHost;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
}
