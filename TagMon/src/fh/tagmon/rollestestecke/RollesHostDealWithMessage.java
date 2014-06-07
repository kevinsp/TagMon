package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;

public class RollesHostDealWithMessage extends RollesHostNetworkMessage implements IHostNetworkMessage{
	
	private final IAbilityComponent abilityComponent;
	


	public RollesHostDealWithMessage(IAbilityComponent abilityComponent){
		super(RollesHostNetworkMessageTypes.DEAL_WITH);
		this.abilityComponent = abilityComponent;
	}
	
	public IAbilityComponent getAbilityComponent() {
		return abilityComponent;
	}
}
