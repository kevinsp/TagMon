package fh.tagmon;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import fh.tagmon.client.gui.Fight;
import fh.tagmon.database.dao.MonsterDAOLocal;
import fh.tagmon.database.daoImpl.MonsterDAOImplLocal;
import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Attribut;
import fh.tagmon.model.Monster;

/*GameEngineModule gEM = new GameEngineModule();

 setContentView(R.layout.activity_main);*/

public class MainActivity extends Activity {

	private final String TAG = "main";

	// private SharedPreferences sharedPref;

	// ///testvariables
	// private String tagMonName = "Spongebob";
	// //

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		


	}

	public void switchToStatsActivity(View view) {
		// Intent myIntent = new Intent(this, Stats.class);
		// startActivity(myIntent);
	}

	public void switchToTrainActivity(View view) {
		// Intent myIntent = new Intent(this, Train.class);
		// startActivity(myIntent);
	}

	public void switchToBattleActivity(View view) {
		Intent myIntent = new Intent(this, Fight.class);
		String battleMode = "normalPvE";
		myIntent.putExtra("battleMode", battleMode);
		startActivity(myIntent);
	}

	/*
	 * public void switchToGenLabActivity(View view) { Intent myIntent = new
	 * Intent(this, GenLab.class); startActivity(myIntent); }
	 */
	// handling button clicks
	public void onBtnClicked(View v) {
		if (v.getId() == R.id.switch_to_train_activity) {
			switchToTrainActivity(v);
		} else if (v.getId() == R.id.switch_to_battle_activity) {
			switchToBattleActivity(v);
		} else if (v.getId() == R.id.switch_to_stats_activity) {
			switchToStatsActivity(v);
		} else if (v.getId() == R.id.switch_to_gen_lab_activity) {
			// switchToGenLabActivity(v);
		}
	}
}
