package fh.tagmon.rollestestecke;

public interface IHostConnection {

	public boolean sendMsgToClient(IHostNetworkMessage msgToClient);
	public IClientNetworkMessage reciveMsgFromClient();
	
}
