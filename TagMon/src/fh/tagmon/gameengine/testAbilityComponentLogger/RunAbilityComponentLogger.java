package fh.tagmon.gameengine.testAbilityComponentLogger;

import android.util.Log;
import fh.tagmon.gameengine.deal_with_incoming_abilitys.AbilityComponentLogger;

public class RunAbilityComponentLogger {

	public void run(){
		AbilityComponentLogger logger = new AbilityComponentLogger();
		
		logger.newRound();
		logger.addLogMsg("BLUB");
		
		Log.i("TEST", logger.getLatestLogMsg());
		
	}
}
