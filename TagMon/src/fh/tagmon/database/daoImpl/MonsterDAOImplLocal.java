package fh.tagmon.database.daoImpl;

import android.content.Context;
import android.database.SQLException;

import java.io.IOException;
import java.util.ArrayList;

import fh.tagmon.database.dao.MonsterDAOLocal;
import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Attribut;
import fh.tagmon.model.AttributModifikator;
import fh.tagmon.model.Koerperteil;
import fh.tagmon.model.KoerperteilArt;
import fh.tagmon.model.Monster;
import fh.tagmon.model.Stats;

public class MonsterDAOImplLocal implements MonsterDAOLocal{

	private DataBaseHelperLocal dbHelper;
	
	public MonsterDAOImplLocal(Context context) throws IOException, SQLException {
		dbHelper = new DataBaseHelperLocal(context);
		
		dbHelper.createDataBase();
		dbHelper.openDataBase();
		
	}
	
	@Override
	public Monster getMonster(int monsterID) throws MonsterDAOException {
		return dbHelper.getMonsterByID(monsterID);
	}
	
	@Override
	public Monster getDummyMonster() throws MonsterDAOException {
		
		Attribut attribut = new Attribut(1, 2, 3, 4);
		
		
		Ability ability = new Ability(1, "Faustschlag", 20, 2, AbilityTargetRestriction.ENEMY);
		
		IAbilityComponent component = new Damage(7, AbilityTargetRestriction.ENEMY);
		ability.addAbilityComponent(component);
		
		IAbilityComponent buffComponent = new Buff(1, 2, AbilityTargetRestriction.SELF, 4, 3, 2);
		ability.addAbilityComponent(buffComponent);
		

		Ability blockAbility = new Ability(2, "BLOCKABILITY", 11,2, AbilityTargetRestriction.SELF);
		blockAbility.addAbilityComponent(new Buff(2,2,AbilityTargetRestriction.SELF,3,5,3));
		
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		abilityList.add(ability);
		abilityList.add(blockAbility);
		
		
		
		
		Koerperteil koerperteil = new Koerperteil(1, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(koerperteil);
		
		Stats stats = new Stats(1,100,100,30,111,100,10,123,13);
		
		Monster dummyMonster = new Monster(1, "MonsterName", "Beschr",attribut, koerperteilList, stats);
		
		return dummyMonster;
	}


	public void deleteMonster(Monster monster) throws MonsterDAOException {
		
	}

	public void updateMonster(Monster monster) throws MonsterDAOException {
		dbHelper.updateMonster(monster);
	}
	
	public void createPlayer(String name){
		dbHelper.createPlayer(name);
	}
	
	
}
