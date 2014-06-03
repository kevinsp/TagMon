package fh.tagmon.network;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;

public final class HostMessageObject {
	private final HostMessageObjectType type;
	private final String msg;
	private final IAbilityComponent abilityComponent;
	
	
	
	public HostMessageObject(HostMessageObjectType type, String msg,
			IAbilityComponent abilityComponent) {
		this.type = type;
		this.msg = msg;
		this.abilityComponent = abilityComponent;
	}

	public HostMessageObjectType getType(){
		return type;
	}
	
	public String getMessage(){
		if(type == HostMessageObjectType.ENEMY_TURN_LOG)
			return msg;
		return "";
	}
	
	public IAbilityComponent getAbilityComponent(){
		if(type == HostMessageObjectType.ABILITY_COMPONENT)
			return abilityComponent;
		return null;
	}
}
