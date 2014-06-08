package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;

public class RollesClientActionMessage extends RollesClientNetworkMessage implements IClientNetworkMessage{

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
