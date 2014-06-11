package fh.tagmon.network;

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


	public NetworkPlayer(IHostConnection connector) {
		super();
		this.connector = connector;
	}
	
	
	@Override
	public PlayerInfo gameStarts(int playersId) {
		MessageObject<?> gameStart = sendMsgAndReceiveAnswer(MessageFactory.getHostMessage_GameStart(playersId));
		return new PlayerInfo((String) gameStart.getContent());
	}
	

	@Override
	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList, int yourTargetId) {
		//ACHTUNG TARGETLIST MUSS GEÄNDERT WERDEN
		MessageObject<?> actionMsg = sendMsgAndReceiveAnswer(MessageFactory.getHostMessage_YourTurn(targetList));
		return (ActionObject) actionMsg.getContent();
	}

	
	@Override
	public AnswerObject dealWithAbilityComponents(AbilityComponentList acl) {
		MessageObject<?> answerMsg = sendMsgAndReceiveAnswer(MessageFactory.getHostMessage_AbilityComponentList(acl));
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
		this.connector.sendMsgToClient(MessageFactory.getHostMessage_GameOver(""));
	}

	
	private MessageObject<?> sendMsgAndReceiveAnswer(MessageObject<?> msg){
		this.connector.sendMsgToClient(msg);
		return connector.reciveMsgFromClient();
	}


}
