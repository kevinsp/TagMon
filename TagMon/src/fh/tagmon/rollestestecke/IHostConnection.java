package fh.tagmon.rollestestecke;

public interface IHostConnection {

	public boolean sendMsgToClient(HostNetworkMessage msgToClient);
	public IClientNetworkMessage reciveMsgFromClient();
	
}
