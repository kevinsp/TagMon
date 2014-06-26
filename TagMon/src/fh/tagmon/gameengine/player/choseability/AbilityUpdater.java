package fh.tagmon.gameengine.player.choseability;

import java.util.HashMap;
import java.util.List;

import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.player.IListener;
import fh.tagmon.model.Monster;

public class AbilityUpdater implements IListener {

	private Monster monster;
	private AbilityStatsUpdater statsUpdater;
	private AbilityTargetUpdater targetUpdater;
	
	public AbilityUpdater(Monster myMonster) {
		this.monster = myMonster;
		this.statsUpdater = new AbilityStatsUpdater();
		this.targetUpdater = new AbilityTargetUpdater();
	}

	@Override
	public void newRound(List<PlayerInfo> targetList, int yourTargetId) {
		this.targetUpdater.prepareAllTargetRestrictions(targetList, yourTargetId);
		this.statsUpdater.prepareAllAbilitys(this.monster);
	}

	
}
