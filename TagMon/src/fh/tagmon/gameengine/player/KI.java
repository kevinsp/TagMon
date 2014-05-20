package fh.tagmon.gameengine.player;


import java.util.HashMap;

import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;


public class KI implements IPlayer {

    private String kiName;
    private Monster myMonster;
    private MonsterPlayModule playModule;
    private int id;
    private EventManager eventManager = new EventManager();

    public KI(String name, Monster myMonster, int id) {
        this.kiName = name;
        this.myMonster = myMonster;
        this.id = id;
        this.playModule = new MonsterPlayModule(myMonster, eventManager);
    }

    private int myRandomWithHigh(int low, int high) {
        // Zufallszahlen inklusive Obergrenze
        high++;
        return (int) (Math.random() * (high - low) + low);
    }

    private Ability choseRandomAbility() {
        int random = this.myRandomWithHigh(0, 1);
        return this.playModule.getPrep().getAbility(random);
    }

    private AbilityTargetRestriction choseRandomTarget(Ability chosenAbility) {
        return chosenAbility.getTargetRestriction().getFirst();
    }

    @Override
    public ActionObject yourTurn(HashMap<Integer, IPlayer> targetList,
                                 int yourTargetId) {
        sendNewRoundEvent(targetList, yourTargetId);
        Ability chosenAbility = this.choseRandomAbility();
        ActionObject action = new ActionObject(chosenAbility, this.choseRandomTarget(chosenAbility));
        return action;
    }

    @Override
    public String getPlayerName() {
        return this.kiName;
    }

    @Override
    public AnswerObject workWithAbilityComponent(
            IAbilityComponent abilityComponent) {
        this.playModule.handleAbilityComponents(abilityComponent);

        boolean isMonsterDead = false;
        if (this.myMonster.getCurrentLifePoints() <= 0) {
            isMonsterDead = true;
        }

        return new AnswerObject(this.playModule.getLatestLogEntry(), isMonsterDead);
    }

    @Override
    public Monster getMonster() {
        return this.myMonster;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void sendNewRoundEvent(HashMap<Integer, IPlayer> targetList, int yourTargetId) {
        this.eventManager.sendNewRoundEvent(targetList, yourTargetId);

    }


}
