package fh.tagmon.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;

public class NetworkSocketConnection extends NetworkConnection implements Runnable{
	private Socket connection;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean running = true;
	
	public NetworkSocketConnection(String host) throws IOException{
		connection = new Socket(host, 21665);
		out = new ObjectOutputStream(connection.getOutputStream());
		in 	= new ObjectInputStream(connection.getInputStream());
	}

	@Override
	public void run() {
		while(running){
			HostMessageObject hostMessage = listenToBroadcast();
			setChanged();
			notifyObservers(hostMessage);
		}
	}
	
	@Override
	public HostMessageObject listenToBroadcast() {
		try {
			return (HostMessageObject) in.readObject();
		} catch (IOException e) {
			System.err.println("Verbindung ist abgebrochen!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} return null;
	}
	
	private synchronized void close(){
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

	@Override
	public void sendActionToHost(ActionObject ao) {
		send(ao);
	}

	@Override
	public void sendAnswerToHost(AnswerObject ao) {
		send(ao);
	}
	
	private <M> void send(M msg){
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeConnection() {
		close();
	}
}
