package fh.tagmon.database.dao;

import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.model.Monster;

public interface MonsterDAOLocal {
	
	/**
	 * Holt ein Monster anhand einer ID aus der Datenbank
	 * 
	 * @param monsterID	{@link Integer}
	 * @return {@link Monster} Objekt das mit Daten aus der Datenbank erstellt worden ist
	 * @throws MonsterDAOException
	 */
	public Monster getMonster(int monsterID) throws MonsterDAOException;
	
	/**
	 * Erstellt ein Dummy Monster und gibt das zur�ck
	 * @return Ein Dummy {@link Monster} Objekt
	 * @throws MonsterDAOException
	 */
	public Monster getDummyMonster() throws MonsterDAOException;
	
	/**
	 * L�scht ein Monster
	 * @param monster Das {@link Monster} das gel�scht werden soll
	 * @throws MonsterDAOException
	 */
	public void deleteMonster(Monster monster) throws MonsterDAOException;
	/**
	 * Updated ein Monster und all seine verbundenen Eigenschaften/K�rperteile usw.
	 * @param monster
	 * @throws MonsterDAOException
	 */
	public void updateMonster(Monster monster) throws MonsterDAOException;
	

}
