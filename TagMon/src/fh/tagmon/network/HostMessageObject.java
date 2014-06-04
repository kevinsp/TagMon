package fh.tagmon.network;

import java.io.Serializable;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerList;

public final class HostMessageObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private final HostMessageObjectType type;
	private final String msg;
	private final IAbilityComponent abilityComponent;
	private final PlayerList playerList;
	
	
	
	public HostMessageObject(HostMessageObjectType type, String msg,
			IAbilityComponent abilityComponent, PlayerList playerList) {
		this.type = type;
		this.msg = msg;
		this.playerList = playerList;
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
	
	public PlayerList getPlayerList(){
		if(type == HostMessageObjectType.YOUR_TURN_ORDER)
			return playerList;
		return null;
	}
}
