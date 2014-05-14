package fh.tagmon.gameengine.player;

import java.util.LinkedList;

import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;

import fh.tagmon.gameengine.MonsterDummys.Monster;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;

public class MyPlayerCreator {

	public static IPlayer getPlayer(String playername, String monsterName){
		
		Monster monster = new Monster(monsterName, 30, 1, 2, 1);
		
		Ability block = getDefenseAbility();
		Ability hit = getOffensiveAbility();
		
		monster.addAbility(block);
		monster.addAbility(hit);
		
		KI newKi = new KI(playername,monster);
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
}
