package fh.tagmon.rollestestecke;

public interface IClientConnector {

	public boolean sendMsgToHost(IClientNetworkMessage msgToHost);
	public IHostNetworkMessage reciveMsgFromHost();
	
}
