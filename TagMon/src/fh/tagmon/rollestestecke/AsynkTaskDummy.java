package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.gameengine.GameHostEngine;
import android.os.AsyncTask;
import android.util.Log;

public class AsynkTaskDummy extends AsyncTask<Void, Void, Void>{

	private GameHostEngine hostEngine = null;
	private RollesTestKi testKi = null;
	
	public AsynkTaskDummy(GameHostEngine hostEngine, RollesTestKi testKi){
		this.hostEngine = hostEngine;
		this.testKi = testKi;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.i("TEST", "A");
		if(this.hostEngine != null){
			this.hostEngine.go();
			Log.i("TEST", "B");
		}
		if(this.testKi != null){
			Log.i("TEST", "C");
			this.testKi.playTheGame();
		}
		
		return null;
	}

}
