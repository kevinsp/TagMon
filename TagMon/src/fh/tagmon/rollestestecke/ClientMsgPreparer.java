package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public class ClientMsgPreparer {

	private IClientConnection myConnection;
	
	public ClientMsgPreparer(IClientConnection connector){
		this.myConnection = connector;
	}
	
	public boolean sendGameStartsMsg(String playerName){
		RollesClientGameStartMessage gameStartMsg = new RollesClientGameStartMessage(playerName);
		this.myConnection.sendMsgToHost(gameStartMsg);
		return true;
	}
	
	public IHostNetworkMessage waitForMsgFromHost(){
		return this.myConnection.reciveMsgFromHost();
	}
	
	public boolean sendActionMsg(ActionObject action){
		RollesClientActionMessage actionMsg = new RollesClientActionMessage(action);
		this.myConnection.sendMsgToHost(actionMsg);
		return true;
	}
	
	public boolean sendAnswerMsg(AnswerObject answer){
		RollesClientAnswerMessage answerMsg = new RollesClientAnswerMessage(answer);
		this.myConnection.sendMsgToHost(answerMsg);
		return true;
	}
}
