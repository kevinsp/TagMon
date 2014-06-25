package fh.tagmon.network.clientConnections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;
import fh.tagmon.network.message.MessageObject;

public class NetworkSocketConnection extends ANetworkConnection{
	private Socket connection;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean running = true;
	private String TAG = "NetworkSocketConnection";
	
	public NetworkSocketConnection(String host) throws IOException{
		connection = new Socket(host, 21665);
		out = new ObjectOutputStream(connection.getOutputStream());
		in 	= new ObjectInputStream(connection.getInputStream());
	}

	@Override
	public void run() {
		while(running){
			MessageObject<?> hostMessage = listenToBroadcast();
			setChanged();
			notifyObservers(hostMessage);
		}
	}
	
	@Override
	public MessageObject<?> listenToBroadcast() {
		try {
			return (MessageObject<?>) in.readObject();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} return null;
	}

	@Override
	public void sendToHost(MessageObject<?> msg) {
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void closeConnection() {
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(connection != null)
				connection.close();
			running = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
