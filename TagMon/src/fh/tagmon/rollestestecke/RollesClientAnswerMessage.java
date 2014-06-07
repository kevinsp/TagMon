package fh.tagmon.rollestestecke;

public class RollesClientAnswerMessage extends RollesClientNetworkMessage{

	private String msg;
	private boolean monsterIsDead;
	
	public RollesClientAnswerMessage(String msg, boolean monsterIsDead){
		super(RollesClientNetworkMessageTypes.ANSWER);
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
