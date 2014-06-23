package fh.tagmon.network.hostConnection;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;
import fh.tagmon.network.message.MessageObject;

public class NetworkServerTCPConnection implements IHostConnection{
	private final Socket connection;
	private final ObjectInputStream in;
	private final ObjectOutputStream out;
	private final String TAG = "IHostConnection";
	
	
	public NetworkServerTCPConnection(Socket socket) throws IOException {
		this.connection = socket;
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out = new ObjectOutputStream(socket.getOutputStream());
		
		
	}
	
	@Override
	public boolean sendMsgToClient(MessageObject<?> msgToClient) {
		try {
			out.writeObject(msgToClient);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public MessageObject<?> reciveMsgFromClient() {

		try {
			return (MessageObject<?>) in.readObject();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} return null;
	}

	public synchronized void closeConnection() {
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(connection != null)
				connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
