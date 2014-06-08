package fh.tagmon.rollestestecke;

public interface IHostConnector {

	public boolean sendMsgToClient(IHostNetworkMessage msgToClient);
	public IClientNetworkMessage reciveMsgFromClient();
	
}
