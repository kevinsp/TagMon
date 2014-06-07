package fh.tagmon.client;

import java.util.HashMap;
import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;
import fh.tagmon.network.HostMessageObject;
import fh.tagmon.network.INetworkListener;
import fh.tagmon.network.INetworkSender;

public class LocalPlayer implements IPlayer{

	@Override
	public ActionObject yourTurn(HashMap<Integer, IPlayer> targetList,
			int yourTargetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnswerObject workWithAbilityComponent(
			IAbilityComponent abilityComponent) {
		// TODO Auto-generated method stub
		return null;
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
	public AbilityTargetRestriction getAbilityTargetRestriction(Ability chosenAbility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerInfo getReady(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
