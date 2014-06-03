package fh.tagmon.network;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public class NetworkConnection implements INetworkSender, INetworkListener{
	private static NetworkConnection instance = null;
	
	public static NetworkConnection getInstance(){
		if(instance == null)
			instance = new NetworkConnection();
		return instance;
	}
	
	@Override
	public HostMessageObject listenToBroadcast() {
		// TODO Auto-generated method stub
		return "Test";
	}

	@Override
	public void sendActionToHost(ActionObject ao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAnswerToHost(AnswerObject ao) {
		// TODO Auto-generated method stub
		
	}
	
}
