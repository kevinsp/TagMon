package fh.tagmon.rollestestecke;


public class FakeSocket implements IClientConnection, IHostConnection{
	
	private volatile HostNetworkMessage  msgFromHost = null;
	private volatile IClientNetworkMessage msgFromClient = null;
	

	public HostNetworkMessage popMsgFromHost(){
		HostNetworkMessage retStr = msgFromHost;
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
	public boolean sendMsgToClient(HostNetworkMessage msg){
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
	public HostNetworkMessage reciveMsgFromHost() {
		while(true){

			HostNetworkMessage msgFromHost = this.popMsgFromHost();
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
