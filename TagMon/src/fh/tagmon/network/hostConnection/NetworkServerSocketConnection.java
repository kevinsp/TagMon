package fh.tagmon.network.hostConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import fh.tagmon.gameengine.gameengine.PlayerList;
import fh.tagmon.network.message.MessageObject;

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
				Socket newClient = this.serverSocket.accept();
				
				Log.i(TAG, "Client accepted");
				
				NetworkServerTCPConnection tcpClientCon = new NetworkServerTCPConnection(newClient);
				
				NetworkPlayer newNetPlayer = new NetworkPlayer(tcpClientCon);
				
				playerList.addPlayer(newNetPlayer);
				
			} catch (IOException e) {
				Log.e(TAG, "SOME PROBS");
				e.printStackTrace();
			}
		}
		return playerList;
	}
	
	


}




