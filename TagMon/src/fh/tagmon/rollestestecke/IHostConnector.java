package fh.tagmon.rollestestecke;

public interface IHostConnector {

	public boolean send(IHostNetworkMessage msgToClient);
	public IClientNetworkMessage recive();
	
}
