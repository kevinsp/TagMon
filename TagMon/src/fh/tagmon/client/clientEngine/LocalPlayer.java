package fh.tagmon.client.clientEngine;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class LocalPlayer implements IPlayer{

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
	public AbilityTargetRestriction getAbilityTargetRestriction(Ability chosenAbility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerInfo getReady(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList,
			int yourTargetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendNewRoundEvent(
			HashMap<Integer, PlayerInfo> playerTargetList,
			int currentPlayerTargetId) {
		// TODO Auto-generated method stub
		
	}
}
