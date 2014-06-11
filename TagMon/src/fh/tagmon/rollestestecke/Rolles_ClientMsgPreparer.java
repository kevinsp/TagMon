package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.network.clientConnections.IClientConnection;
import fh.tagmon.network.message.MessageFactory;
import fh.tagmon.network.message.MessageObject;

public class Rolles_ClientMsgPreparer {

	private IClientConnection myConnection;
	
	public Rolles_ClientMsgPreparer(IClientConnection connector){
		this.myConnection = connector;
	}
	
	public MessageObject<?> waitForMsgFromHost(){
		return this.myConnection.listenToBroadcast();
	}
	
	public void sendGameStartsMsg(String playerName){
		this.myConnection.sendToHost(MessageFactory.createClientMessage_GameStart(playerName, 0));
	}
	
	public void sendActionMsg(ActionObject action){
		this.myConnection.sendToHost(MessageFactory.createClientMessage_Action(action, 0));
	}
	
	public void sendAnswerMsg(AnswerObject answer){
		this.myConnection.sendToHost(MessageFactory.createClientMessage_Answer(answer,	0));
	}
}
