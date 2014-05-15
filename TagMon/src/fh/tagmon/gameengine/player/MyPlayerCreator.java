package fh.tagmon.gameengine.player;

import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;

public class MyPlayerCreator {

	public static IPlayer getPlayer(String playername, String monsterName, int id){
		
		Monster monster = new Monster(monsterName, 30, 1, 2, 1);
		
		Ability block = getDefenseAbility();
		Ability hit = getOffensiveAbility();
		
		monster.addAbility(block);
		monster.addAbility(hit);

		KI newKi = new KI(playername,monster, id);
		return newKi;
	}
	
	public static Ability getDefenseAbility(){
		IAbilityComponent comp = new Buff(1,0,0,0,3);
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
	
	public static Monster getMonsterWithAbil(String monsterName){
		Monster monster = new Monster(monsterName, 30, 1, 2, 1);
		
		Ability block = getDefenseAbility();
		Ability hit = getOffensiveAbility();
		
		monster.addAbility(block);
		monster.addAbility(hit);
		
		return monster;
	}
/*
    public static IPlayer getPlayerNotKi(String playername, String monsterName) {

        Monster monster = new Monster(monsterName, 30, 1, 2, 1);

        Ability block = getDefenseAbility();
        Ability hit = getOffensiveAbility();

        monster.addAbility(block);
        monster.addAbility(hit);

        Player newPlayer = new Player(playername,monster);
        return newPlayer;
    }
    */
}
