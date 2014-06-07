package fh.tagmon.exception;

public class MonsterDAOException extends Exception  {
	public MonsterDAOException() {
		super("Could not create Monster");
	}
	
	public MonsterDAOException(String msg) {
		super(msg);
	}
}
