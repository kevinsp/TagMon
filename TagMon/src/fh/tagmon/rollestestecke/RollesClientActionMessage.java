package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.helperobjects.ActionObject;

public class RollesClientActionMessage extends RollesClientNetworkMessage implements RollesIClientNetworkMessage{

	private final ActionObject action;
	
	protected RollesClientActionMessage(ActionObject action){
		super(RollesClientNetworkMessageTypes.ACTION);
		this.action = action;

	}


	@Override
	public RollesClientNetworkMessageTypes getMessageType() {
		return super.messageType;
	}


	public ActionObject getAction() {
		return action;
	}
	
}
