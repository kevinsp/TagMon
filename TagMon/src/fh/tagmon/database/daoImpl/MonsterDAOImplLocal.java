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
import fh.tagmon.gameengine.abilitys.Heal;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.Schadensabsorbation;
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
		
		Attribut attribut = new Attribut(1, 4, 3, 4);
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		
		
		Ability ability = new Ability(1, "Heilung", 30, 2, AbilityTargetRestriction.SELF);	
		IAbilityComponent component = new Heal(25, AbilityTargetRestriction.SELF);
		ability.addAbilityComponent(component);
		abilityList.add(ability);
		Koerperteil koerperteil = new Koerperteil(1, "Kopf", abilityList, KoerperteilArt.KOPF, new AttributModifikator(0, 0, 0));
		koerperteilList.add(koerperteil);
		
		
		ability = new Ability(2, "Schadensabsorbation", 20, 3, AbilityTargetRestriction.SELF);	
		component = new Schadensabsorbation(2, 20, AbilityTargetRestriction.SELF);
		abilityList = new ArrayList<Ability>();
		koerperteil = new Koerperteil(2, "Torso", abilityList, KoerperteilArt.TORSO, new AttributModifikator(0, 0, 0));
		ability.addAbilityComponent(component);
		abilityList.add(ability);
		koerperteilList.add(koerperteil);
		
		
		ability = new Ability(3, "Faustschlag", 25, 1, AbilityTargetRestriction.ENEMY);	
		component = new Damage(25, AbilityTargetRestriction.ENEMY);
		abilityList = new ArrayList<Ability>();
		koerperteil = new Koerperteil(2, "Arm", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
		ability.addAbilityComponent(component);
		abilityList.add(ability);
		koerperteilList.add(koerperteil);

		
		ability = new Ability(4, "Roundhousekick", 40, 2, AbilityTargetRestriction.ENEMY);	
		component = new Damage(40, AbilityTargetRestriction.ENEMY);
		abilityList = new ArrayList<Ability>();
		koerperteil = new Koerperteil(4, "Bein", abilityList, KoerperteilArt.BEIN, new AttributModifikator(0, 0, 0));
		ability.addAbilityComponent(component);
		abilityList.add(ability);
		koerperteilList.add(koerperteil);
		

		Stats stats = new Stats(1, 60, 60, 80, 80, 15, 1, 0, 3);
		
		Monster dummyMonster = new Monster(1, "Tagzilla", "Beschreibung", attribut, koerperteilList, stats);
		
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
