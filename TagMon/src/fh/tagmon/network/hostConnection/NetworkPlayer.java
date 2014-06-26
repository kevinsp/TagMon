package fh.tagmon.network.hostConnection;

import java.util.List;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.gameengine.IHostPlayer;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.helperobjects.SummaryObject;
import fh.tagmon.gameengine.player.PlayerInfo;
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
		MessageObject<?> gameStart = sendMsgAndReceiveAnswer(MessageFactory.createHostMessage_GameStart(playersId));
		return ((PlayerInfo) gameStart.getContent());
	}
	

	@Override
	public ActionObject yourTurn(List<PlayerInfo> targetList, int yourTargetId) {

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
	public void printSummary(SummaryObject msg) {
		this.connector.sendMsgToClient(MessageFactory.createHostMessage_Summary(msg));
	}


}
