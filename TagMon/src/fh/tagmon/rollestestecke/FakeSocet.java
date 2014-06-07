package fh.tagmon.rollestestecke;


public class FakeSocet {
	
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
	
	public void setMsgFromHost(IHostNetworkMessage msg){
		this.msgFromHost = msg;
	}
	
	public void setMsgFromClient(IClientNetworkMessage msg){
		this.msgFromClient = msg;
	}
	
	public IHostNetworkMessage scanForHostMsg(){
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

	public IClientNetworkMessage scanForClientMsg(){
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
}
