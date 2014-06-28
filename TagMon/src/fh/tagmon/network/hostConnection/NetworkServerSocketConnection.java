package fh.tagmon.network.hostConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;
import fh.tagmon.gameengine.gameengine.PlayerList;

public class NetworkServerSocketConnection {

	private final int port;
	private final int maxPlayerSize;
	private ServerSocket serverSocket ;
	
	private final String TAG = "NetworkServerSocketConnection";
	
	public NetworkServerSocketConnection(int maxPlayerSize) throws IOException{
		this.port = 21665;
		this.maxPlayerSize = maxPlayerSize;
		this.serverSocket = new ServerSocket(21665);
	}
	
	
	public PlayerList getPlayers(){
		PlayerList playerList = new PlayerList();
		for(int i = 0; i < maxPlayerSize; i++){
			try {
				Log.i(TAG, "Waiting for Client ....");
				Socket newClient = this.serverSocket.accept();
				
				Log.i(TAG, "Client accepted");
				NetworkServerTCPConnection tcpClientCon = new NetworkServerTCPConnection(newClient);
				
				Log.i(TAG, "building NetworkPlayer");
				NetworkPlayer newNetPlayer = new NetworkPlayer(tcpClientCon);
				
				Log.i(TAG, "Adding NetworkPlayer to the Playlist");
				playerList.addPlayer(newNetPlayer);
				
			} catch (IOException e) {
				Log.e(TAG, "SOME PROBS");
				e.printStackTrace();
			}
		}
		Log.i(TAG, "All Players have connected and are now in the PlayerList");
		return playerList;
	}
	
	public int getPort(){
		return port;
	}
}




