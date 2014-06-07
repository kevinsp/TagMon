package fh.tagmon.rollestestecke;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class HostConnector implements IPlayer{

	private IHostConnector connector;


	public HostConnector(IHostConnector connector) {
		super();
		this.connector = connector;
	}

	@Override
	public ActionObject yourTurn(HashMap<Integer, IPlayer> targetList,
			int yourTargetId) {
		//ACHTUNG TARGETLIST MUSS GE�NDERT WERDEN
		
		RollesHostYourTurnMessage yourTurnMsg = new RollesHostYourTurnMessage(targetList, yourTargetId);
		this.connector.send(yourTurnMsg);
		RollesClientActionMessage action = (RollesClientActionMessage) connector.recive();
		
		return new ActionObject(action.getAbility(), action.getTargetRestriction());
	}

	@Override
	public String getPlayerName() {
		return "";
	}

	@Override
	public AnswerObject workWithAbilityComponent(
			IAbilityComponent abilityComponent) {
		RollesHostDealWithMessage dealWithMsg = new RollesHostDealWithMessage(abilityComponent);
		
		this.connector.send(dealWithMsg);
		
		RollesClientAnswerMessage answer = (RollesClientAnswerMessage) connector.recive();
		
		return new AnswerObject(answer.getMsg(), answer.isMonsterDead());
	}

	@Override
	public Monster getMonster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendNewRoundEvent(HashMap<Integer, IPlayer> playerTargetList,
			int currentPlayerTargetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbilityTargetRestriction getAbilityTargetRestriction(
			Ability chosenAbility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerInfo getReady(int id) {
		RollesHostGameStartMessage gameStartMsg = new RollesHostGameStartMessage(id);
		this.connector.send(gameStartMsg);
		
		RollesClientGameStartMessage gameStart= (RollesClientGameStartMessage) this.connector.recive();
		
		return new PlayerInfo(gameStart.getPlayerName());
	}
	
	

}
