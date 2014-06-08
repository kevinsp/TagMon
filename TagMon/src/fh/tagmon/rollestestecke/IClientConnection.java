package fh.tagmon.rollestestecke;

public interface IClientConnection {

	public boolean sendMsgToHost(IClientNetworkMessage msgToHost);
	public IHostNetworkMessage reciveMsgFromHost();
	
}
