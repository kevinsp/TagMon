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
	 * Erstellt ein Dummy Monster und gibt das zurück
	 * @return Ein Dummy {@link Monster} Objekt
	 * @throws MonsterDAOException
	 */
	public Monster getDummyMonster() throws MonsterDAOException;
	
	/**
	 * Löscht ein Monster
	 * @param monster Das {@link Monster} das gelöscht werden soll
	 * @throws MonsterDAOException
	 */
	public void deleteMonster(Monster monster) throws MonsterDAOException;
	/**
	 * Updated ein Monster und all seine verbundenen Eigenschaften/Körperteile usw.
	 * @param monster
	 * @throws MonsterDAOException
	 */
	public void updateMonster(Monster monster) throws MonsterDAOException;
	

}
