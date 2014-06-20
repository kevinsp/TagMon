package fh.tagmon.database.dao;

import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.model.Monster;

public interface MonsterDAOLocal {
	
	public Monster getMonster(int monsterID) throws MonsterDAOException;
	public Monster getDummyMonster() throws MonsterDAOException;
	public void deleteMonster(Monster monster) throws MonsterDAOException;
	public void updateMonster(Monster monster) throws MonsterDAOException;
	

}
