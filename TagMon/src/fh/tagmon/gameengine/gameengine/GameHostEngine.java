package fh.tagmon.gameengine.gameengine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.guiParts.Fight;


public class GameHostEngine extends AsyncTask implements Runnable {

    private PlayerList playerList;
    private IPlayer currentPlayer;
    private int currentRoundStatus = 0;
    private int roundCounter = 0;
    private Context context = null;
    private boolean runGame = true;
    private final Object waitForPlayer;
    private boolean wait;
    private ActionObject actionFromUser;

    public GameHostEngine(PlayerList newPList, Context context) {
        this.playerList = newPList;
        this.context = context;

        waitForPlayer = new Object();
        wait = false;
    }

    //function for testing for rolle
    @Override
    public void run() {
        ((Fight) context).initBattleGUI(playerList.getPlayList(), 0); // player id is '0' for testing

        while (runGame) {
            this.roundCounter++;
            // 1Phase Neuer Spieler
            currentPlayer = this.playerList.getNextPlayer();

            // 2Phase
            ActionObject action = currentPlayer.yourTurn(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());

            myLogger("##################### ROUND: " + String.valueOf(this.roundCounter));
            myLogger("CurrentPlayer: " + currentPlayer.getPlayerName());
            String targetName = this.playerList.getPlayerByTargetId(action.getTargetRestriction().getTargetList().getFirst()).getPlayerName();
            myLogger("Chosen Ability: |" + action.getAbility().getAbilityName() + "| on Target: " + targetName);

            // 3phase Ability Zerlegen und Komponenten an den Richtigen Schicken
            this.sendAbilityComponentToPlayer(action);
        }

    }

    private ActionObject waitForAction() {
        ActionObject action = null;


        if (currentPlayer.getId() == 0) {
            onPause();

            ((Fight) context).chooseAbility(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId(), this);
            currentPlayer.sendNewRoundEvent(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());

            synchronized (waitForPlayer) {
                while (wait) {
                    try {
                        waitForPlayer.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            action = actionFromUser;
        } else {
            action = currentPlayer.yourTurn(this.playerList.getPlayerTargetList(), this.playerList.getCurrentPlayerTargetId());
        }
        return action;
    }

    /**
     * Call this on pause.
     */
    public void onPause() {
        synchronized (waitForPlayer) {
            wait = true;
        }
    }

    /**
     * Call this on resume.
     */
    public void onResume() {
        synchronized (waitForPlayer) {
            wait = false;
            waitForPlayer.notifyAll();
        }
    }

    /**
    * call this function from the Fight Activity to set the user choosen action
    **/
    public void setActionFromUser(ActionObject action) {
        actionFromUser = action;
        onResume();
    }

    private void myLogger(String toLog) {
        Log.i("GameEngine", toLog);
    }


    private void sendAbilityComponentToPlayer(ActionObject action) {
        Ability ability = action.getAbility();
        for (IAbilityComponent aComponent : ability.getAbilityComponents()) {
            LinkedList<Integer> targetList = null;

            switch (aComponent.getComponentTargetRestriction()) {
                case DEFAULT:
                    AbilityTargetRestriction abiTarRes = action.getTargetRestriction();
                    targetList = abiTarRes.getTargetList();
                    break;
                case ENEMY:
                case SELF:
                    targetList = aComponent.getComponentTargetRestriction().getTargetList();
                    break;
                default:
                    break;
            }

            this.sendToList(aComponent, targetList);
        }

    }

    private void sendToList(IAbilityComponent aComponent, LinkedList<Integer> targetList) {
        for (Integer targetId : targetList) {
            final IPlayer player = this.playerList.getPlayerByTargetId(targetId);
            AnswerObject answer = player.workWithAbilityComponent(aComponent);

//            ((Fight) context).refreshGUI(player, "life");


            myLogger("==== Answer from Player: " + player.getPlayerName() + " ====");
            myLogger(answer.getMsg());
            myLogger("====");
            /////////////////////////////////////////// TESTHALBER
            if (answer.isMonsterDead()) {
                this.runGame = false;
            }
            //////////////////////////////
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        //for testing
        ((Fight) context).initBattleGUI(playerList.getPlayList(), 0); // player id is '0' for testing

        while (runGame) {

            this.roundCounter++;
            // 1Phase Neuer Spieler
            currentPlayer = this.playerList.getNextPlayer();

            // 2Phase Spieler soll seinen Zugmachen
            ActionObject action = waitForAction();

            myLogger("##################### ROUND: " + String.valueOf(this.roundCounter));
            myLogger("CurrentPlayer: " + currentPlayer.getPlayerName());
            String targetName = this.playerList.getPlayerByTargetId(action.getTargetRestriction().getTargetList().getFirst()).getPlayerName();
            myLogger("Chosen Ability: |" + action.getAbility().getAbilityName() + "| on Target: " + targetName);

            // 3phase Ability Zerlegen und Komponenten an den Richtigen Schicken
            this.sendAbilityComponentToPlayer(action);
        }
        return null;
    }
}
