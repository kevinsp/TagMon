package fh.tagmon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fh.tagmon.guiParts.Fight;
import fh.tagmon.guiParts.Stats;
import fh.tagmon.guiParts.Train;

/*GameEngineModule gEM = new GameEngineModule();

        setContentView(R.layout.activity_main);*/

public class MainActivity extends Activity {

    private final String TAG = "main";

   // private SharedPreferences sharedPref;


    /////testvariables
    //private String tagMonName = "Spongebob";
    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // app = ((GlobalVariables) getApplicationContext());
        //TODO: check if first run!
     /*   onFirstRun();
        initTagMon();
        */

    }

    //TODO: call this function on the first time the app is started,
    //TODO: create TagMon etc, save tagmon in sql lite db
  /*  public void onFirstRun() {
        if (app.getTagMon() == null) {
            app.setTagMon(new TagMon(this.tagMonName));
        }
    }


    private void initTagMon() {
        TagMon tagMon = app.getTagMon();
        if (tagMon != null) {
            TextView tagMonLevelTextView = (TextView) findViewById(R.id.tagMonLevel);
            tagMonLevelTextView.setText(String.valueOf(tagMon.getLevel()));
            tagMonLevelTextView.setTextAppearance(getApplicationContext(), R.style.standardDarkeningBackGroundAndTextColor);

            TextView tagMonNameTextView = (TextView) findViewById(R.id.tagMonName);
            tagMonNameTextView.setText(String.valueOf(tagMon.getName()));
            tagMonNameTextView.setTextAppearance(getApplicationContext(), R.style.standardDarkeningBackGroundAndTextColor);

            ImageView image = (ImageView) findViewById(R.id.tagMonImage);
            String enemyDrawable = tagMon.getDrawable();
            int resID = getResources().getIdentifier(enemyDrawable, "drawable", getPackageName());
            image.setImageResource(resID);
        }
    }
*/
    public void switchToStatsActivity(View view) {
        Intent myIntent = new Intent(this, Stats.class);
        startActivity(myIntent);
    }

    public void switchToTrainActivity(View view) {
        Intent myIntent = new Intent(this, Train.class);
        startActivity(myIntent);
    }

    public void switchToBattleActivity(View view) {
        Intent myIntent = new Intent(this, Fight.class);
        String battleMode = "normalPvE";
        myIntent.putExtra("battleMode", battleMode);
        startActivity(myIntent);
    }
/*
    public void switchToGenLabActivity(View view) {
        Intent myIntent = new Intent(this, GenLab.class);
        startActivity(myIntent);
    }
*/
    //handling button clicks
    public void onBtnClicked(View v) {
        if (v.getId() == R.id.switch_to_train_activity) {
            switchToTrainActivity(v);
        } else if (v.getId() == R.id.switch_to_battle_activity) {
            switchToBattleActivity(v);
        } else if (v.getId() == R.id.switch_to_stats_activity) {
            switchToStatsActivity(v);
        } else if (v.getId() == R.id.switch_to_gen_lab_activity) {
          //  switchToGenLabActivity(v);
        }
    }
}

