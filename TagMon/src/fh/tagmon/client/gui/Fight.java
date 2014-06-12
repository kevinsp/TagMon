package fh.tagmon.client.gui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fh.tagmon.R;
import fh.tagmon.client.clientEngine.GameClientEngine;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.GameEngineModule;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.gameengine.PlayerListNode;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;


public class Fight extends Activity implements fh.tagmon.client.gui.IBattleGUI {

    private final String TAG = "fight";
    private int userId;
    private static boolean battleGuiInit = false;
    private Context context = this;
    private DialogBuilder chooseDialog;
    private GameEngineModule engineModule;
    private ISetAbility iSetAbility;

    private IPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        //init game engine
        //Todo : Kondition einbauen f""r nicht-host-spieler
        engineModule = new GameEngineModule(this);

    }


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
    public boolean initBattleGUI(LinkedList<PlayerInfo> players, int userId, LinkedList<Ability> abilities) {
        if (!battleGuiInit) {
            battleGuiInit = true;
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
        } else {
            return false;
        }
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
    @Override
    public void refreshGUI(final IPlayer player, final Enum<GuiPartsToUpdate> partToUpdate) {
        if (battleGuiInit) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fh.tagmon.model.Monster monster = player.getMonster();
                    if (partToUpdate == GuiPartsToUpdate.HEALTH) {
                        int currentLife = monster.getCurrentLifePoints();
                        int maxLife = monster.getMaxLifePoints();
                        //Log.d(TAG, "id: " + player.getId() + " -  current: " + currentLife);
                        if (player.getId() == userId) {
                            refreshUserLife(maxLife, currentLife);
                        } else {
                            refreshEnemyLife(maxLife, currentLife);
                        }
                    }

                }
            });
        } else {
            //TODO: do something on error, throw exception for example
        }

    }
*/
    /*
    @desc:
    opens a dialog with all possible attacks the player can use
    and let the player choose one of them

    @params:
        //TODO documentation

    */
    /*
    @Override
    public void chooseAbility(HashMap<Integer, IPlayer> targetList, int yourTargetId, ISetAbility setAbility) {
        this.iSetAbility = setAbility;
        HashMap<Integer, IPlayer> targetListF = targetList;
        int yourTargetIdF = yourTargetId;

        player = targetListF.get(yourTargetIdF);
        LinkedList<Ability> abilities = player.getMonster().getAbilitys();

        List<String> abilityNames = new ArrayList<String>();
        for (Ability ability : abilities) {
            String abilityName = ability.getAbilityName();
            abilityNames.add(abilityName);
        }
        final CharSequence[] items = abilityNames.toArray(new CharSequence[abilityNames.size()]);

        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if (chooseDialog != null) {
                                  chooseDialog.dismiss();
                              }
                              chooseDialog = new DialogBuilder(context, getString(R.string.chooseAbility), items, null, chooseAbilityListener);
                          }
                      }
        );
    }
    */

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


    //disable buttons
    public void toggleButtons(boolean enable) {
        findViewById(R.id.tryToEscape).setEnabled(enable);
        findViewById(R.id.chooseAttack).setEnabled(enable);
        findViewById(R.id.openInventory).setEnabled(enable);
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
        battleGuiInit = false;
        finish();
    }


    //click listeners
    //listener after an ability was choosen, choose the target category (enemy, self, etc)
    private final View.OnClickListener chooseAbilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d(TAG, v.getTag().toString());
            try {
             /*   TableLayout table = (TableLayout) v.getParent().getParent();
                if (table != null) {
                    final IPlayer player = (IPlayer) table.getTag();

*/
                int item = Integer.parseInt(v.getTag().toString());


                final Ability choosenAbility = player.getMonster().getAbilitys().get(item);
                // ist keine liste mehr
                //final LinkedList<AbilityTargetRestriction> atr = player.getAbilityTargetRestriction(choosenAbility);
                final AbilityTargetRestriction atr = player.getAbilityTargetRestriction(choosenAbility);
                
                List<String> targetNames = new ArrayList<String>();
                //for (Enum target : atr) {
                    String targetName = atr.name();
                    targetNames.add(targetName);
                //}

                final CharSequence[] targetItems = targetNames.toArray(new CharSequence[targetNames.size()]);

                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (chooseDialog != null) {
                                          chooseDialog.dismiss();
                                      }
                                      chooseDialog = new DialogBuilder(context, getString(R.string.chooseTarget), targetItems, choosenAbility, chooseTargetRestrictionListener);
                                  }
                              }
                );
                //   }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    };

    //listener after an ability and an target category was choosen, choose the concret target
    private final View.OnClickListener chooseTargetRestrictionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d(TAG, v.getTag().toString());
            try {
                int item = Integer.parseInt(v.getTag().toString());
                TableLayout table = (TableLayout) v.getParent().getParent();
                if (table != null) {
                    final Ability choosenAbility = (Ability) table.getTag();
                    //ist keine liste mehr
                    //LinkedList<AbilityTargetRestriction> atr = player.getAbilityTargetRestriction(choosenAbility);
                    AbilityTargetRestriction atr = player.getAbilityTargetRestriction(choosenAbility);
                    LinkedList<Integer> targetList = atr.getTargetList();

                    List<String> targetNames = new ArrayList<String>();
                    for (Integer target : targetList) {
                        targetNames.add(target.toString());
                    }

                    final CharSequence[] targetItems = targetNames.toArray(new CharSequence[targetNames.size()]);
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (chooseDialog != null) {
                                              chooseDialog.dismiss();
                                          }
                                          chooseDialog = new DialogBuilder(context, getString(R.string.chooseTarget), targetItems, choosenAbility, sendActionToGamePlayEngineListener);
                                      }
                                  }
                    );
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
    //listener after an ability and an target category was choosen, choose the concret target
    private final View.OnClickListener sendActionToGamePlayEngineListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                int item = Integer.parseInt(v.getTag().toString());
                TableLayout table = (TableLayout) v.getParent().getParent();
                if (table != null) {
                    Ability choosenAbility = (Ability) table.getTag();
                    iSetAbility.setAbility(new ActionObject(choosenAbility, choosenAbility.getTargetRestriction()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public void showTemporaryDialog(String content) {
        DialogBuilder db = new DialogBuilder(this, "Zusammenfassung", content, -1);
    }

    @Override
    public void refreshGUI(int id, final Enum<GuiPartsToUpdate> partToUpdate, Object value) {
        if (battleGuiInit) {

        } else {
            //TODO: do something on error, throw exception for example
        }
    }

    @Override
    public void chooseAbility(ISetAbility setAbility) {
     /*   this.iSetAbility = setAbility;
        HashMap<Integer, IPlayer> targetListF = targetList;
        int yourTargetIdF = yourTargetId;

        player = targetListF.get(yourTargetIdF);
        LinkedList<Ability> abilities = player.getMonster().getAbilitys();

        List<String> abilityNames = new ArrayList<String>();
        for (Ability ability : abilities) {
            String abilityName = ability.getAbilityName();
            abilityNames.add(abilityName);
        }
        final CharSequence[] items = abilityNames.toArray(new CharSequence[abilityNames.size()]);

        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if (chooseDialog != null) {
                                  chooseDialog.dismiss();
                              }
                              chooseDialog = new DialogBuilder(context, getString(R.string.chooseAbility), items, null, chooseAbilityListener);
                          }
                      }
        );*/
    }
}
