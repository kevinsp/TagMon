package fh.tagmon.gameengine.gameengine;

import java.util.HashMap;
import java.util.List;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.helperobjects.*;
import fh.tagmon.gameengine.player.PlayerInfo;

public interface IHostPlayer {

	public ActionObject yourTurn(List<PlayerInfo> targetList, int yourTargetId);
	public AnswerObject dealWithAbilityComponents(AbilityComponentList acl);
	public AnswerObject dealWithAbilityComponent(IAbilityComponent iac);
	public void	printSummary(SummaryObject msg);
    public PlayerInfo gameStarts(int playersId);
    public void gameOver();
}

