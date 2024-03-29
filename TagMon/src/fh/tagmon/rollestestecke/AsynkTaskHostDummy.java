/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.rollestestecke;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
import fh.tagmon.gameengine.gameengine.GameHostEngine;
import fh.tagmon.gameengine.gameengine.PlayerList;
import fh.tagmon.network.hostConnection.NetworkServerSocketConnection;

public class AsynkTaskHostDummy extends AsyncTask<Void, Void, Void>{

	private GameHostEngine hostEngine = null;
	private int gamePlayerSize = 0;
	private String TAG = "AsynkTaskHostDummy";
	
	public AsynkTaskHostDummy(int gamePlayerSize){
		this.gamePlayerSize = gamePlayerSize;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			NetworkServerSocketConnection server = new NetworkServerSocketConnection(this.gamePlayerSize);
			PlayerList playerList = server.getPlayers();
			hostEngine = new GameHostEngine(playerList);
			this.hostEngine.go();
			server.closeServerSocket();
			
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		

		return null;
	}

}
