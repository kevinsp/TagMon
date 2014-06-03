package fh.tagmon.network;

import fh.tagmon.gameengine.gameengine.GameHostEngine;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public class LocalConnection implements INetworkSender, INetworkListener{
	private static NetworkConnection instance = null;
	private GameHostEngine host;
	
	public static NetworkConnection getInstance(){
		if(instance == null)
			instance = new NetworkConnection();
		return instance;
	}
	
	@Override
	public HostMessageObject listenToBroadcast() {
		while(true){
			HostMsg = socket.recive();
			clientEng.send(HostMsg)
		}
		return null;
	}

	@Override
	public void sendActionToHost(ActionObject ao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAnswerToHost(AnswerObject ao) {
		// TODO Auto-generated method stub
		
	}
	
	public void registerHost(GameHostEngine host){
		this.host = host;
	}
}
