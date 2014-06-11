package fh.tagmon.gameengine.gameengine;

import java.util.HashMap;


import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;


public interface IHostPlayer {

	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList, int yourTargetId);
	public AnswerObject dealWithAbilityComponent(IAbilityComponent abilityComponent);
    public PlayerInfo gameStarts(int playersId);
    public void gameOver();
}

