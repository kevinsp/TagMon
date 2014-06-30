package fh.tagmon.gameengine.helperobjects;

import java.io.Serializable;

import fh.tagmon.gameengine.player.PlayerInfo;



public class AnswerObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1661241189250692821L;
	private StringBuilder msg = new StringBuilder();
	private PlayerInfo playerInfo;
	private boolean monsterIsDead = false;;
	private final String STOP = "\n";
	
	public AnswerObject(){}
	public AnswerObject(String msg){
		appendMsg(msg + STOP);
	}
	public AnswerObject(String msg, boolean monsterIsDead){
		appendMsg(msg + STOP);
		this.monsterIsDead = monsterIsDead;
	}
	
	public void setMonsterIsDead(boolean dead){
		monsterIsDead = dead;
		if(dead)
			msg.append("Das Monster ist besiegt worden!" + STOP);
	}
	public boolean isMonsterDead(){
		return this.monsterIsDead;
	}
	
	public void appendMsg(String msg){
		this.msg.append(msg + STOP);
	}
	public String getMsg(){
		return this.msg.toString();
	}
    public void setPlayerInfo(PlayerInfo pi){
        playerInfo = pi;
    }
	public PlayerInfo getPlayerInfo(){
		return playerInfo;
	}
}
