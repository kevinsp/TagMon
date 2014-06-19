package fh.tagmon.exception;

public class MonsterDAOException extends Exception  {
	private static final long serialVersionUID = 1L;

	public MonsterDAOException() {
		super("Could not create Monster");
	}
	
	public MonsterDAOException(String msg) {
		super(msg);
	}
}
