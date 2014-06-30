package fh.tagmon.client.gui;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fh.tagmon.R;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.GameEngineModule;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;
import fh.tagmon.rollestestecke.MyMonsterCreator;


public class Fight extends Activity implements fh.tagmon.client.gui.IBattleGUI {

    private final String TAG = "fight";
    private int userId;
    private static boolean battleGuiInit = false;
    private Context context = this;
    private DialogBuilder chooseDialog;
    private GameEngineModule engineModule;
    private String enemyName = "";
    private ISetAbility iSetAbility;
    private List<Ability> abilities;
    private DialogBuilder summaryDialog;
    private String playername = "";




    private Ability lastChoosenAbility = null;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        disableButtons();
        Intent intent = getIntent();
        String monsterId = intent.getStringExtra("monsterId");

        //init game engine
        //Todo : Kondition einbauen fuer nicht-host-spieler


/*
        Monster monster = null;
        MonsterDAOLocal monsterDAO = null;

        try {
            monsterDAO = new MonsterDAOImplLocal(this);
        } catch (SQLException e1) {
            Log.d("tagmonDB", "SQLEX");
        } catch (IOException e1) {
            Log.d("tagmonDB", "IOEX");
        }

        try {
            monster = monsterDAO.getMonster(Integer.parseInt(monsterId,10));

        } catch (MonsterDAOException e){
            Log.d("tagmonDB", "MonsterDAO");
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("tagmonDB", "FAIIIIIIIIIIIIIIL");
            e.printStackTrace();
        }*/

        MyMonsterCreator mCreator = new MyMonsterCreator();

        Monster blueM = mCreator.getMonsterDummy();

        engineModule = new GameEngineModule(this, blueM);
        engineModule.startGamePlayerVSTag("408bcf6gff"); // die eig SerienNr vom gescanten Tag übergeben

        //mock-up
        /*
        MyMonsterCreator mCreator = new MyMonsterCreator();
        Monster redM = mCreator.getMonsterDummy();
        this.abilities = redM.getAbilitys();
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if (chooseDialog != null) {
                                  chooseDialog.dismiss();
                              }
                              chooseDialog = new DialogBuilder(context, getString(R.string.chooseAbility), abilities, null, chooseAbilityListener, DialogAction.CHOOSE_ABILITY);
                          }
                      }
        );*/
    }


    /**
     * ** functions ****
     */
        /*
        @desc:
        init the gui when starting a battle

        @params:


        @return:
        boolean: true if the GUI could initialize successful, otherwise false
        */
    public boolean initBattleGUI(List<PlayerInfo> players, int userId, List<Ability> abilities) {
        if (!battleGuiInit) {
            battleGuiInit = true;
            this.userId = userId;
            this.abilities = abilities;

            for (PlayerInfo player : players) {
                String playerName = player.NAME;

                if (player.ID == this.userId) {
                    playername = playerName;
                    initUserGui(playerName);
                } else {
                    this.enemyName = playerName;
                    initEnemyGui(playerName);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void initUserGui(final String name) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {



                              //refreshUserLife(maxLife, currentLife);

                              //level
                              // TextView ownLevelView = (TextView) findViewById(R.id.ownLevel);
                              // ownLevelView.setText(String.valueOf(level));

                              //                      ImageView image = (ImageView) findViewById(R.id.ownImage);
       /* String enemyDrawable = tagMon.getDrawable();
        int resID = getResources().getIdentifier(enemyDrawable, "drawable", getPackageName());
        image.setImageResource(resID);
        */

                              //name
                              TextView ownNameView = (TextView) findViewById(R.id.ownName);
                              ownNameView.setText(name);
                          }
                      }
        );
    }
    public void initEnemyGui(final String name) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              //set the image for the enemy
                              //ImageView image = (ImageView) findViewById(R.id.enemyImage);
       /* String enemyDrawable = tagMon.getDrawable();
        int resID = getResources().getIdentifier(enemyDrawable, "drawable", getPackageName());
        image.setImageResource(resID);
        */

                              //  refreshEnemyLife(maxLife, currentLife);
                              //level
                              //   TextView enemyLevelView = (TextView) findViewById(R.id.enemyLevel);
                              // enemyLevelView.setText(String.valueOf(level));

                              //name
                              TextView enemyNameView = (TextView) findViewById(R.id.enemyName);
                              enemyNameView.setText(name);
                          }
                      }
        );
    }

    public void setEnemyHead(final String imgName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    ImageView imgView = (ImageView) findViewById(R.id.enemyHead);
                    int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    imgView.setImageResource(resID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }});
    }

    public void setEnemyBody(final String imgName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView imgView = (ImageView) findViewById(R.id.enemyBody);
                    int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    imgView.setImageResource(resID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }});
    }
    public void setEnemyLeftLeg(final String imgName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView imgView = (ImageView) findViewById(R.id.enemyLeftLegBack);
                    ImageView imgView2 = (ImageView) findViewById(R.id.enemyLeftLegFront);
                    int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    imgView.setImageResource(resID);
                    imgView2.setImageResource(resID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }});
    }
    public void setEnemyRightLeg(final String imgName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView imgView = (ImageView) findViewById(R.id.enemyRightLegBack);
                    ImageView imgView2 = (ImageView) findViewById(R.id.enemyRightLegFront);
                    int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    imgView.setImageResource(resID);
                    imgView2.setImageResource(resID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }});
    }



    public void refreshUserLife(final int maxLife, final int currentLife) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ImageView> al = new ArrayList<ImageView>();
                ArrayList<Integer> lifes = new ArrayList<Integer>();

                int curHp100 = currentLife / 100, curHp010 = currentLife%100 / 10, curHp001 = currentLife%10,
                        maxHp100 = maxLife /100, maxHp010 = maxLife%100 / 10, maxHp001 = maxLife%10;

                lifes.add(curHp001);
                lifes.add(curHp010);
                lifes.add(curHp100);
                lifes.add(maxHp001);
                lifes.add(maxHp010);
                lifes.add(maxHp100);

                ImageView ivCurHp100 = (ImageView) findViewById(R.id.curHp100);
                ImageView ivCurHp010 = (ImageView) findViewById(R.id.curHp010);
                ImageView ivCurHp001 = (ImageView) findViewById(R.id.curHp001);

                ImageView ivMaxHp100 = (ImageView) findViewById(R.id.maxHp100);
                ImageView ivMaxHp010 = (ImageView) findViewById(R.id.maxHp010);
                ImageView ivMaxHp001 = (ImageView) findViewById(R.id.maxHp001);
                al.add(ivCurHp001);
                al.add(ivCurHp010);
                al.add(ivCurHp100);
                al.add(ivMaxHp001);
                al.add(ivMaxHp010);
                al.add(ivMaxHp100);

                int counter = 0;
                for (ImageView iv : al) {
                    //String drawableName = context.getResources().getResourceName(iv.getId());
                    int life = lifes.get(counter);
                    String drawableName = "hp" + life;
                    int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    iv.setImageResource(resID);
                    counter++;
                }
                //71    28
                float density = context.getResources().getDisplayMetrics().density;

                float life = (float)currentLife / (float) maxLife;
                float dpToLeft = life * 71;
                ImageView healthBar = (ImageView) findViewById(R.id.ownHealthBar);
                if (dpToLeft < 71-28) {
                    //dpToLeft = -(71-28-dpToLeft);
                    dpToLeft = (int)-((71*density - 28*density - dpToLeft*density));

                } else {
                    //dpToLeft = 71-dpToLeft+28;
                    dpToLeft = (int)((71-dpToLeft)*density + 28*density);
                }
                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(healthBar.getLayoutParams());

                marginParams.setMargins((int)(dpToLeft), (int) (55*density), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                healthBar.setLayoutParams(layoutParams);
            }});
    }


    public void refreshEnemyLife(final int maxLife,final int currentLife) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ImageView> al = new ArrayList<ImageView>();
                ArrayList<Integer> lifes = new ArrayList<Integer>();

                int curHp100 = currentLife / 100, curHp010 = currentLife%100 / 10, curHp001 = currentLife%10,
                        maxHp100 = maxLife /100, maxHp010 = maxLife%100 / 10, maxHp001 = maxLife%10;

                lifes.add(curHp001);
                lifes.add(curHp010);
                lifes.add(curHp100);
                lifes.add(maxHp001);
                lifes.add(maxHp010);
                lifes.add(maxHp100);

                ImageView ivCurHp100 = (ImageView) findViewById(R.id.enemycurHp100);
                ImageView ivCurHp010 = (ImageView) findViewById(R.id.enemycurHp010);
                ImageView ivCurHp001 = (ImageView) findViewById(R.id.enemycurHp001);

                ImageView ivMaxHp100 = (ImageView) findViewById(R.id.enemymaxHp100);
                ImageView ivMaxHp010 = (ImageView) findViewById(R.id.enemymaxHp010);
                ImageView ivMaxHp001 = (ImageView) findViewById(R.id.enemymaxHp001);
                al.add(ivCurHp001);
                al.add(ivCurHp010);
                al.add(ivCurHp100);
                al.add(ivMaxHp001);
                al.add(ivMaxHp010);
                al.add(ivMaxHp100);

                int counter = 0;
                for (ImageView iv : al) {
                    //String drawableName = context.getResources().getResourceName(iv.getId());
                    int life = lifes.get(counter);
                    String drawableName = "hp" + life;
                    int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    iv.setImageResource(resID);
                    counter++;
                }
                //71    28
                float density = context.getResources().getDisplayMetrics().density;

                float life = (float)currentLife / (float) maxLife;
                float dpToLeft = life * 71;
                ImageView healthBar = (ImageView) findViewById(R.id.enemyHealthBar);
                if (dpToLeft < 71-28) {
                    //dpToLeft = -(71-28-dpToLeft);
                    dpToLeft = (int)-((71*density - 28*density - dpToLeft*density));

                } else {
                    //dpToLeft = 71-dpToLeft+28;
                    dpToLeft = (int)((71-dpToLeft)*density + 28*density);
                }
                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(healthBar.getLayoutParams());

                marginParams.setMargins((int)(dpToLeft), (int) (55*density), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                healthBar.setLayoutParams(layoutParams);
            }});
    }


    //try to escape from the fight
    public void tryToEscape() {
        finishActivity();
    }

    //handling button clicks
    public void onBtnClicked(View v) {
        if (v.getId() == R.id.chooseAttack) {
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (chooseDialog != null) {
                                      chooseDialog.dismiss();
                                  }
                                  chooseDialog = new DialogBuilder(context, getString(R.string.chooseAbility), abilities, null, chooseAbilityListener, DialogAction.CHOOSE_ABILITY);
                              }
                          }
            );
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

                lastChoosenAbility = abilities.get(item);
                // ist keine liste mehr
                //final LinkedList<AbilityTargetRestriction> atr = player.getAbilityTargetRestriction(choosenAbility);
                final AbilityTargetRestriction atr = lastChoosenAbility.getTargetRestriction();

                final List<String> targetNames = new ArrayList<String>();
                //for (Enum target : atr) {
                String targetName = atr.name();
                targetNames.add(targetName);
                //}

                // final CharSequence[] targetItems = targetNames.toArray(new CharSequence[targetNames.size()]);

                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (chooseDialog != null) {
                                          chooseDialog.dismiss();
                                      }
                                      chooseDialog = new DialogBuilder(context, getString(R.string.chooseTarget), targetNames, lastChoosenAbility, chooseTargetRestrictionListener, DialogAction.CHOOSE_TARGET_RESTRICTION);
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
                //int item = Integer.parseInt(v.getTag().toString());
                TableRow tableRow = (TableRow) v;
                if (tableRow != null) {
                    // int targetRestrictionChoosen = (Integer) tableRow.getTag();

                    //final Ability choosenAbility = (Ability) abilities.get(targetRestrictionChoosen);
                    //ist keine liste mehr
                    //LinkedList<AbilityTargetRestriction> atr = player.getAbilityTargetRestriction(choosenAbility);

                    AbilityTargetRestriction atr = lastChoosenAbility.getTargetRestriction();

                    /*
                    * for the mockup mockup
                    *

                    atr.addTarget(0);
                    atr.addTarget(1);
*/
                    LinkedList<Integer> targetList = atr.getTargetList();

                    final List<String> targetNames = new ArrayList<String>();
                    for (Integer target : targetList) {
                        //targetNames.add(target.toString());
                        if (target == userId) {
                            targetNames.add(playername);
                        } else {
                            targetNames.add(enemyName);
                        }

                    }

                    //final CharSequence[] targetItems = targetNames.toArray(new CharSequence[targetNames.size()]);

                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (chooseDialog != null) {
                                              chooseDialog.dismiss();
                                          }
                                          chooseDialog = new DialogBuilder(context, getString(R.string.chooseTarget), targetNames, lastChoosenAbility, sendActionToGamePlayEngineListener, DialogAction.CHOOSE_TARGET);
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
                // int item = Integer.parseInt(v.getTag().toString());
                TableRow tableRow = (TableRow) v;
                if (tableRow != null) {
                    chooseDialog.dismiss();
                    disableButtons();
                    int choosenEnemyIdInList = (Integer) tableRow.getTag();
                    AbilityTargetRestriction atr = lastChoosenAbility.getTargetRestriction();
                    int choosenEnemyId = atr.getTargetList().get(choosenEnemyIdInList);
                    atr.cleanTargetList();
                    atr.addTarget(choosenEnemyId);
                    iSetAbility.setAbility(new ActionObject(lastChoosenAbility, atr));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public void showTemporaryDialog(final String content, final ISetAbility onresumeDialog) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                summaryDialog = new DialogBuilder(context, "Zusammenfassung", content, -1);
                summaryDialog.setOnDismissListener((new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                     //   onresumeDialog.onResumeDialog();
                    }

                }));
            }});
    }

    @Override
    public void refreshGUI(final List<PlayerInfo> players, final Enum<GuiPartsToUpdate> partToUpdate) {
        if (battleGuiInit && !players.isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (partToUpdate == GuiPartsToUpdate.HEALTH) {
                        for (PlayerInfo playerInfo : players) {
                            if (playerInfo != null) {
                                int maxLife = playerInfo.getMaxLife();
                                int currentLife = playerInfo.getCurrentLife();
                                if (playerInfo.ID == userId) {
                                    refreshUserLife(maxLife, currentLife);
                                } else {
                                    refreshEnemyLife(maxLife, currentLife);
                                }
                            }
                        }
                    }
                }

            });
        }
    }

    @Override
    public void chooseAbility(ISetAbility setAbility) {
        this.iSetAbility = setAbility;
        enableButtons();

        /*
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if (chooseDialog != null) {
                                  chooseDialog.dismiss();
                              }
                              chooseDialog = new DialogBuilder(context, getString(R.string.chooseAbility), abilities, null, chooseAbilityListener, DialogAction.CHOOSE_ABILITY);
                          }
                      }
        );*/
    }

    public void disableButtons(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button btn = (Button) findViewById(R.id.chooseAttack);
                btn.setEnabled(false);
            }});
    }
    public void enableButtons(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button btn = (Button) findViewById(R.id.chooseAttack);
                btn.setEnabled(true);
            }});
    }
    @Override
    public void handleGameOver(final String gameOverMessage, final ISetAbility gce) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                summaryDialog = new DialogBuilder(context, "Zusammenfassung", gameOverMessage, -1);
                summaryDialog.setOnDismissListener((new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                       
                        gce.closeGame();
                        finishActivity();
                    }

                }));
            }});
    }

}
