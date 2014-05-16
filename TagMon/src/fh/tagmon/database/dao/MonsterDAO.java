package fh.tagmon.database.dao;

import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.model.Monster;

public interface MonsterDAO {
	
	public Monster getMonster(String tagID) throws MonsterDAOException;
	public void delete(Monster monster) throws MonsterDAOException;
	public void update(Monster monster) throws MonsterDAOException;
	

}
