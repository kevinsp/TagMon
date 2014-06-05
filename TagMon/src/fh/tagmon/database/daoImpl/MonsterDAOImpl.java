package fh.tagmon.database.daoImpl;

import java.util.ArrayList;

import android.content.Context;
import fh.tagmon.database.dao.MonsterDAO;
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

public class MonsterDAOImpl implements MonsterDAO {

	private DataBaseHelper dbHelper;
	
	public MonsterDAOImpl(Context context) {
		dbHelper = new DataBaseHelper(context);
	}
	
	@Override
	public Monster getMonster(String tagID) throws MonsterDAOException {
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Monster getDummyMonster() throws MonsterDAOException {
		Attribut attribut = new Attribut(0, 10, 11, 12, 13);
		Ability ability = new Ability("Beiﬂattacke", 20, AbilityTargetRestriction.ENEMY);
		
		IAbilityComponent component = new Damage(7, AbilityTargetRestriction.ENEMY);
		ability.addAbilityComponent(component);
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		
		Ability blockAbility = new Ability("BLOCKABILITY", 11, AbilityTargetRestriction.SELF);
		blockAbility.addAbilityComponent(new Buff(2,0,5,0,0,null));
		abilityList.add(blockAbility);
		
		
		abilityList.add(ability);
		
		Koerperteil koerperteil = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0, 0));
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(koerperteil);
		Stats stats = new Stats(0,100,100,30,111,100,10,123);
		Monster dummyMonster = new Monster(0, "MonsterName", attribut, koerperteilList, stats);
		
		return dummyMonster;
	}

	@Override
	public void delete(Monster monster) throws MonsterDAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Monster monster) throws MonsterDAOException {
		// TODO Auto-generated method stub
		
	}

	

	
}
