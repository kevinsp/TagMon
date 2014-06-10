package fh.tagmon.rollestestecke;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.IHostPlayer;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class NetworkPlayer implements IHostPlayer{

	private IHostConnection connector;


	public NetworkPlayer(IHostConnection connector) {
		super();
		this.connector = connector;
	}

	@Override
	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList, int yourTargetId) {
		//ACHTUNG TARGETLIST MUSS GEÄNDERT WERDEN
		
		HostNetworkMessage yourTurnMsg = new HostNetworkMessage(HostNetworkMessageTypes.YOUR_TURN);
		yourTurnMsg.addYourTurnMessage(yourTargetId, targetList);
		this.connector.sendMsgToClient(yourTurnMsg);
		
		RollesClientActionMessage actionMsg = (RollesClientActionMessage) connector.reciveMsgFromClient();
		
		return actionMsg.getAction();
	}


	@Override
	public AnswerObject dealWithAbilityComponent(IAbilityComponent abilityComponent) {
		HostNetworkMessage dealWithMsg = new HostNetworkMessage(HostNetworkMessageTypes.DEAL_WITH_INCOMING_ABILITY_COMPONENT);
		dealWithMsg.addDealWithIncomingAbilityComponentMessage(abilityComponent);
		
		this.connector.sendMsgToClient(dealWithMsg);
		
		RollesClientAnswerMessage answerMsg = (RollesClientAnswerMessage) connector.reciveMsgFromClient();
		
		return answerMsg.getAnswer();
	}


	@Override
	public PlayerInfo gameStarts(int playersId) {
		HostNetworkMessage gameStartMsg = new HostNetworkMessage(HostNetworkMessageTypes.GAME_START);
		gameStartMsg.addGameStartsMessage(playersId);
		
		this.connector.sendMsgToClient(gameStartMsg);
		
		RollesClientGameStartMessage gameStart= (RollesClientGameStartMessage) this.connector.reciveMsgFromClient();
		
		return new PlayerInfo(gameStart.getPlayerName());
	}

	@Override
	public void gameOver() {
		HostNetworkMessage gameOverMsg = new HostNetworkMessage(HostNetworkMessageTypes.GAME_OVER);
		this.connector.sendMsgToClient(gameOverMsg);
	}
	
	

}
