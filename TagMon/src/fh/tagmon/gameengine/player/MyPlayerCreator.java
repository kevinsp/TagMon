package fh.tagmon.gameengine.player;


import fh.tagmon.database.dao.MonsterDAO;
import fh.tagmon.database.daoImpl.MonsterDAOImpl;
import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class MyPlayerCreator {

	public static IPlayer getPlayer(String playername, String monsterName, int id, boolean KI){
		
//		Monster monster = new Monster(monsterName, 30, 1, 2, 1);
		
//		Ability block = getDefenseAbility();
//		Ability hit = getOffensiveAbility();
//		
//		monster.addAbility(block);
//		monster.addAbility(hit);
		
		
		MonsterDAO monsterDAO = new MonsterDAOImpl();
		Monster monster = null;
		try {
			monster = monsterDAO.getDummyMonster();
		} catch (MonsterDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (KI)  {
            return new KI(playername,monster, id);
        } else{
            return new Player(playername,monster, id);
        }

	}
	
	public static Ability getDefenseAbility(){
		IAbilityComponent comp = new Buff(1,0,0,0,5);
		AbilityTargetRestriction restriction = AbilityTargetRestriction.SELF;
		Ability abl = new Ability("Block",0,restriction);
		abl.addAbilityComponent(comp);
		return (abl);
	}
	
	public static Ability getOffensiveAbility(){
		IAbilityComponent comp = new Damage(2);
		AbilityTargetRestriction restriction = AbilityTargetRestriction.ENEMY;
		Ability abl = new Ability("Attack",0,restriction);
		abl.addAbilityComponent(comp);
		return (abl);
	}
	
//	public static Monster getMonsterWithAbil(String monsterName){
//		Monster monster = new Monster(monsterName, 30, 1, 2, 1);
//		
//		Ability block = getDefenseAbility();
//		Ability hit = getOffensiveAbility();
//		
//		monster.addAbility(block);
//		monster.addAbility(hit);
//		
//		return monster;
//	}
}
