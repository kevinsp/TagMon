package fh.tagmon.network;

import java.util.Observable;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public abstract class NetworkConnection extends Observable implements INetworkSender, INetworkListener{
	
	@Override
	public abstract HostMessageObject listenToBroadcast();

	@Override
	public abstract void sendActionToHost(ActionObject ao);
	
	@Override
	public abstract void sendAnswerToHost(AnswerObject ao);
	
	@Override
	public abstract void closeConnection();
	
}
