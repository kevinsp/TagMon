package fh.tagmon.network.hostConnection;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.gameengine.IHostPlayer;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.network.message.MessageFactory;
import fh.tagmon.network.message.MessageObject;

public class NetworkPlayer implements IHostPlayer{

	private IHostConnection connector;
	//TODO hier noch festlegen, wer npcs IDs zuteilt
	private final int ID = -1;


	public NetworkPlayer(IHostConnection connector) {
		super();
		this.connector = connector;
	}
	
	
	@Override
	public PlayerInfo gameStarts(int playersId) {
		MessageObject<?> gameStart = sendMsgAndReceiveAnswer(MessageFactory.createHostMessage_GameStart(playersId));
		return new PlayerInfo((String) gameStart.getContent(), ID);
	}
	

	@Override
	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList, int yourTargetId) {
		//ACHTUNG TARGETLIST MUSS GEÄNDERT WERDEN
		MessageObject<?> actionMsg = sendMsgAndReceiveAnswer(MessageFactory.createHostMessage_YourTurn(targetList));
		return (ActionObject) actionMsg.getContent();
	}

	
	@Override
	public AnswerObject dealWithAbilityComponents(AbilityComponentList acl) {
		MessageObject<?> answerMsg = sendMsgAndReceiveAnswer(MessageFactory.createHostMessage_AbilityComponentList(acl));
		return (AnswerObject) answerMsg.getContent();
	}
	
	@Override
	public AnswerObject dealWithAbilityComponent(IAbilityComponent iac) {
		//TODO playerID
		AbilityComponentList list = new AbilityComponentList(0);
		list.addAbilityCommponent(iac);
		return dealWithAbilityComponents(list);
	}

	
	@Override
	public void gameOver() {
		this.connector.sendMsgToClient(MessageFactory.createHostMessage_GameOver(""));
	}

	
	private MessageObject<?> sendMsgAndReceiveAnswer(MessageObject<?> msg){
		this.connector.sendMsgToClient(msg);
		return connector.reciveMsgFromClient();
	}


	@Override
	public void printSummary(String msg) {
		this.connector.sendMsgToClient(MessageFactory.createHostMessage_Summary(msg));
	}


}
