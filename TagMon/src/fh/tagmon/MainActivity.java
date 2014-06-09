package fh.tagmon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fh.tagmon.guiParts.Fight;
//import fh.tagmon.guiParts.Stats;
//import fh.tagmon.guiParts.Train;

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

        // Start of MonsterDAO test
        
//        Monster monster = null;
//        MonsterDAO monsterDAO = null;
//        
//		try {
//			monsterDAO = new MonsterDAOImpl(this);
//		} catch (SQLException e1) {
//			Log.d("tagmonDB", "SQLEX");
//		} catch (IOException e1) {
//			Log.d("tagmonDB", "IOEX");
//		}
//		
//        try {
//			monster = monsterDAO.getMonster("tag1");
//        } catch (MonsterDAOException e){
//        	Log.d("tagmonDB", "MonsterDAO");
//			e.printStackTrace();
//		} catch (Exception e) {
//			Log.d("tagmonDB", "FAIIIIIIIIIIIIIIL");
//			e.printStackTrace();
//		}
//        
//        Log.d("tagmonDB", "GETTING INTEL: " + monster.getIntelligence());
//        Log.d("tagmonDB", "KONSTITUTION: " + monster.getConstitution());
//        Log.d("tagmonDB", "CURR LIFE POINTS " + monster.getCurrentLifePoints());
        
        // END of MonsterDAO test
        
    }

    public void switchToStatsActivity(View view) {
//        Intent myIntent = new Intent(this, Stats.class);
//        startActivity(myIntent);
    }

    public void switchToTrainActivity(View view) {
//        Intent myIntent = new Intent(this, Train.class);
//        startActivity(myIntent);
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

