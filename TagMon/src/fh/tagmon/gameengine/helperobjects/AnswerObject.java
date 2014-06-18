package fh.tagmon.gameengine.helperobjects;

import java.io.Serializable;

public class AnswerObject implements Serializable{

	private String msg;
	private boolean monsterIsDead;
	
	public AnswerObject(String msg, boolean monsterIsDead){
		this.msg = msg;
		this.monsterIsDead = monsterIsDead;
	}
	
	public boolean isMonsterDead(){
		return this.monsterIsDead;
	}
	
	public String getMsg(){
		return this.msg;
	}
}
