package fh.tagmon.gameengine.gameengine;

import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.*;

public interface IHostPlayer {

	public ActionObject yourTurn(HashMap<Integer, PlayerInfo> targetList, int yourTargetId);
	public AnswerObject dealWithAbilityComponents(AbilityComponentList acl);
	public AnswerObject dealWithAbilityComponent(IAbilityComponent iac);
    public PlayerInfo gameStarts(int playersId);
    public void gameOver();
}

