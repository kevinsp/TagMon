package fh.tagmon.rollestestecke;

import java.util.ArrayList;

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

public class MyMonsterCreator {

	
	public Monster getMonsterDummy(){
		
		//stats
		Stats stats = new Stats(0,30,30,30,111,100,10,123,11);
		
		//Attribute
		Attribut attribut = new Attribut(0, 10, 11, 12);
		
		//Abilitys
		Ability beiAttacke =  getBeiAttacke();
		Ability block = getBlockAbility();
		Ability absorb = getAbsorbAbility();
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		abilityList.add(beiAttacke);
		abilityList.add(block);
		abilityList.add(absorb);
		
		//Krperteile
		Koerperteil arm = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
		
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(arm);
		
		//setze Monster zusammen
		Monster dummyMonster = new Monster(0, "MonsterName","test Monster", attribut, koerperteilList, stats);
		
		return dummyMonster;
	}
	
	
	public Monster getAbsorberMonster(){
		//stats
		Stats stats = new Stats(0,30,30,30,111,100,10,123,11);
		
		//Attribute
		Attribut attribut = new Attribut(0, 10, 11, 12);
		
		//Abilitys		
		Ability absorb = getAbsorbAbility();
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		abilityList.add(absorb);
		
		//Krperteile
		Koerperteil arm = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
		
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(arm);
		
		//setze Monster zusammen
		Monster dummyMonster = new Monster(0, "MonsterName","test", attribut, koerperteilList, stats);
		
		return dummyMonster;
	}
	
	public Monster getAttackMonster(){
		//stats
		Stats stats = new Stats(0,30,30,30,111,100,10,123,11);
		
		//Attribute
		Attribut attribut = new Attribut(0, 10, 11, 12);
		
		//Abilitys		
		Ability beiAttacke =  getBeiAttacke();
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		abilityList.add(beiAttacke);
		
		//Krperteile
		Koerperteil arm = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
		
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(arm);
		
		//setze Monster zusammen
		Monster dummyMonster = new Monster(0, "MonsterName","test", attribut, koerperteilList, stats);
		
		return dummyMonster;
	}
	
	public Monster getHealMonster(){
		//stats
				Stats stats = new Stats(0,30,30,30,111,100,10,123,11);
				
				//Attribute
				Attribut attribut = new Attribut(0, 10, 11, 12);
				
				//Abilitys		
				Ability heal =  getHeal();
				
				ArrayList<Ability>	abilityList = new ArrayList<Ability>();
				abilityList.add(heal);
				
				//Krperteile
				Koerperteil arm = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0));
				
				ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
				koerperteilList.add(arm);
				
				//setze Monster zusammen
				Monster dummyMonster = new Monster(0, "MonsterName","test", attribut, koerperteilList, stats);
				
				return dummyMonster;
	}
	
	private Ability getHeal(){
		Ability ability = new Ability(1, "Healoraisor", 20,0, AbilityTargetRestriction.SELF);
		IAbilityComponent component = new Heal(10, AbilityTargetRestriction.SELF);
		ability.addAbilityComponent(component);
		return ability;
	}
	
	private Ability getBeiAttacke(){
		Ability ability = new Ability(1, "Beissattacke", 20,0, AbilityTargetRestriction.ENEMY);
		IAbilityComponent component = new Damage(40, AbilityTargetRestriction.ENEMY);
		ability.addAbilityComponent(component);
		return ability;
	}
	
	private Ability getBlockAbility(){
		Ability blockAbility = new Ability(2, "BLOCKABILITY", 11,0, AbilityTargetRestriction.SELF);
		IAbilityComponent buff = new Buff(1,2,null,1,2,2);
		blockAbility.addAbilityComponent(buff);
		return blockAbility;
	}
	
	private Ability getAbsorbAbility(){
		Ability absorbAbility = new Ability(3, "Absorber", 10,0, AbilityTargetRestriction.SELF);
		IAbilityComponent schadensAbsorb = new Schadensabsorbation(3, 25, null);
		absorbAbility.addAbilityComponent(schadensAbsorb);
		return absorbAbility;
	}
}
