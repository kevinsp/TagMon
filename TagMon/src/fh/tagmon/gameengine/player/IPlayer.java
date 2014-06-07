package fh.tagmon.gameengine.player;

import java.util.HashMap;
import java.util.LinkedList;





import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public interface IPlayer {
	
	public ActionObject yourTurn(HashMap<Integer, IPlayer> targetList, int yourTargetId);
	public String getPlayerName();
	public AnswerObject workWithAbilityComponent(IAbilityComponent abilityComponent);
	public Monster getMonster();
    public int getId();
    void sendNewRoundEvent(HashMap<Integer, IPlayer> playerTargetList, int currentPlayerTargetId);
    public AbilityTargetRestriction getAbilityTargetRestriction(Ability chosenAbility);
    public PlayerInfo getReady(int id);
}
