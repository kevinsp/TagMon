package fh.tagmon.guiParts;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

import fh.tagmon.R;
import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.GameEngineModule;
import fh.tagmon.gameengine.gameengine.PlayerListNode;
import fh.tagmon.gameengine.player.IPlayer;


public class Fight extends Activity implements fh.tagmon.guiParts.IBattleGUI {

    private final String TAG = "fight";
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);


        //init game engine

        GameEngineModule gEM = new GameEngineModule(this);

/*
        IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster");
        IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster");
        PlayerList playerList = new PlayerList();
        playerList.addPlayer(redKi);
        playerList.addPlayer(blueKi);

        GamePlayEngine playEngine = new GamePlayEngine(playerList);
        playEngine.run();
*/


        // initBattleGUI(playerList.getPlayList(), 0);

    }


    /**
     * ** variables ****
     */
        /* flag if battleGui is initialized */
    //   private static boolean battleGuiInit = false;

    //  private static ArrayList<View> dynamicViews = null;

    /* constants for refreshing the battleGui */
    //public static final int REFRESH_LIFE = 0;
    // public static final int REFRESH_ENERGY = 1;


    /**
     * ** functions ****
     */
        /*
        @desc:
        init the gui when starting a battle
        set the battleGuiInit flag to true

        @params:
        players: A list with all participating players in this battle,
        every element of 'players' should contain the id of the player,
        the name, if it's an enemy or an ally and the life/energy of
        the player's TagMon

        @return:
        boolean: true if the GUI could initialize successful, otherwise false
        */
    public boolean initBattleGUI(LinkedList<PlayerListNode> players, int userId) {
        this.userId = userId;
        for (PlayerListNode playerNode : players) {
            IPlayer player = playerNode.getPlayer();
            String playerName = player.getPlayerName();
            Monster playerMonster = player.getMonster();

            int level = 5; //TODO: remove hardcoded

            if (player.getId() == userId) {
                initUserGui(playerMonster.getMaxLifePoints(), playerMonster.getCurrentLifePoints(), level, playerName);
            } else {
                initEnemyGui(playerMonster.getMaxLifePoints(), playerMonster.getCurrentLifePoints(), level, playerName);
            }
       }

        return true;
    }

    @Override
    public void refreshGUI(IPlayer player, String attr) {
        Monster monster = player.getMonster();
        if (attr.equals("life")) {
            int currentLife = monster.getCurrentLifePoints();
            int maxLife = monster.getMaxLifePoints();
            Log.d(TAG, "id: "+ player.getId() + " -  current: " + currentLife);
            if (player.getId() ==  this.userId) {
                refreshUserLife(maxLife, currentLife);
            } else {
                refreshEnemyLife(maxLife, currentLife);
            }
        }

    }

    @Override
    public Ability chooseAbility(HashMap<Integer, IPlayer> targetList, int yourTargetId) {
        return null;
    }


    public void initUserGui(int maxLife, int currentLife, int level, String name) {

        refreshUserLife(maxLife, currentLife);
        //level

        TextView ownLevelView = (TextView) findViewById(R.id.ownLevel);
        ownLevelView.setText(String.valueOf(level));

        //name
        TextView ownNameView = (TextView) findViewById(R.id.ownName);
        ownNameView.setText(name);

    }

    public void refreshUserLife(int maxLife, int currentLife) {

        //health bar
        ProgressBar ownHealthBar = (ProgressBar) findViewById(R.id.ownHealthBar);
        ownHealthBar.setMax(maxLife);
        ownHealthBar.setProgress(currentLife);
        TextView ownHealthBarNumberTextView = (TextView) findViewById(R.id.ownHealthBarNumber);
        String ownLifeProgress = String.format("%d/%d", currentLife, maxLife);
        ownHealthBarNumberTextView.setText(ownLifeProgress);
    }


    public void refreshEnemyLife(int maxLife, int currentLife) {
        //health bar
        ProgressBar enemyHealthBar = (ProgressBar) findViewById(R.id.enemyHealthBar);
        enemyHealthBar.setMax(maxLife);
        enemyHealthBar.setProgress(currentLife);
        TextView enemyHealthBarNumberTextView = (TextView) findViewById(R.id.enemyHealthBarNumber);
        String enemyLifeProgress = String.format("%d/%d", currentLife, maxLife);
        enemyHealthBarNumberTextView.setText(enemyLifeProgress);
    }

    public void initEnemyGui(int maxLife, int currentLife, int level, String name) {

        //set the image for the enemy
        ImageView image = (ImageView) findViewById(R.id.enemyImage);
       /* String enemyDrawable = tagMon.getDrawable();
        int resID = getResources().getIdentifier(enemyDrawable, "drawable", getPackageName());
        image.setImageResource(resID);
        */

        refreshEnemyLife(maxLife, currentLife);
        //level
        TextView enemyLevelView = (TextView) findViewById(R.id.enemyLevel);
        enemyLevelView.setText(String.valueOf(level));

        //name
        TextView enemyNameView = (TextView) findViewById(R.id.enemyName);
        enemyNameView.setText(name);
    }


        /*
        @desc:
        refresh the battleGUI
        battleGUI need to be initialized before

        @params:
        playerId: the id of the player where an attribute has changed

        refreshAttribute: the attribute which should be refreshed
        possible values are defined on the top

        absValue: the absolute value to which the 'refreshAttribute' is changed to
        */
/*
    public static void refreshBattleGui(int playerId, int refreshAttribute, int absValue) {
        if (battleGuiInit) {
            //search for the correct view
              /*
                for (view in dynamicViews) {
                    boolean match = false;
                    //check if playerId and the attribute the view represantate match the params
                    //TODO: matching algorithm
                    if (match) {
                       //call an refresh function depending on the matchted view
                    }
                }
                */
    /*    } else {
            //GUI isn't initialized. throw error?
        }
    }
*/

        /*
        @desc:
        opens a dialog with all possible attacks the player can use
        and let the player choose one of them

        @params:
        abilitys: a list of possible 'Ability' object

        @returns:
        Ability: the ability the player choose to use on which tagmon
        */

    //Ability
    /*
    public static void showPossibleAttacks(ArrayList<Ability> abilitys) {
        //make a list for the abilitys to choose from
        // for (a in abilitys) {
        //TODO: make views
        // set listeners to the views, onClick: decide if default/enemy/self (enums)
        // make new views from the enemy/self and set listeners
        // onClick: decide which enemy to use the ability on
        //    }

    }
*/
        /*
        @desc:
        should be called after the battle ended
        destroys the battleGui
        set battleGuiInit to false
        */
/*
    public static void destroyBattleGui() {
        //reset the view
        battleGuiInit = false;
        dynamicViews = null;
    }
    */


    //disable buttons
    public void disableButtons() {
        findViewById(R.id.tryToEscape).setEnabled(false);
        findViewById(R.id.chooseAttack).setEnabled(false);
        findViewById(R.id.openInventory).setEnabled(false);
    }

    //try to escape from the fight
    public void tryToEscape() {
        finishActivity();
    }

    //handling button clicks
    public void onBtnClicked(View v) {
        if (v.getId() == R.id.chooseAttack) {
            // showAttackPossibilites(v);
            // disableButtons();
        } else if (v.getId() == R.id.openInventory) {
            // openInventory();
        } else if (v.getId() == R.id.tryToEscape) {
            tryToEscape();
        }
    }

    public void finishActivity() {
        finish();
    }


}
