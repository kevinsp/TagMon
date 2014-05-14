package fh.tagmon.gameengine.helperobjects;

public class AnswerObject {

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
