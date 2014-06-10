package fh.tagmon.rollestestecke;

import java.io.Serializable;
import java.util.HashMap;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.gameengine.PlayerInfo;
import fh.tagmon.gameengine.player.IPlayer;

public class HostNetworkMessage implements Serializable{

	private HostNetworkMessageTypes messageType;
	private IAbilityComponent abilityComponent = null;
	private int playersId = -1;
	private HashMap<Integer, PlayerInfo> targetList = null;

	public HostNetworkMessage(HostNetworkMessageTypes messageType){
		this.messageType = messageType;
	}
	
	
	public void addYourTurnMessage(int playersId, HashMap<Integer, PlayerInfo> targetList ){
		this.targetList = targetList;
		this.playersId = playersId;
	}
	
	
	public void addDealWithIncomingAbilityComponentMessage(IAbilityComponent abilityComponent){
		this.abilityComponent = abilityComponent;
	}
	
	public void addGameStartsMessage(int playersId){
		this.playersId = playersId;
	}
	
	public void addGameOverMessage(){
		//evtl noch was adden
	}
	
	public HostNetworkMessageTypes getMessageType(){
		return this.messageType;
	}

	public IAbilityComponent getAbilityComponent(){
		return this.abilityComponent;
	}
	
	public int getPlayersId(){
		return this.playersId;
	}
	
	public HashMap<Integer, PlayerInfo> getTargetList(){
		return this.targetList;
	}
}
