package fh.tagmon.rollestestecke;

public interface IClientConnector {

	public boolean send(IClientNetworkMessage msgToHost);
	public IClientNetworkMessage redive();
	
}
