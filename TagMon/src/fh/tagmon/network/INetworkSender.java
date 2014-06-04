package fh.tagmon.network;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public interface INetworkSender {
	public void sendActionToHost(ActionObject ao);
	public void sendAnswerToHost(AnswerObject ao);
	public void closeConnection();
}
