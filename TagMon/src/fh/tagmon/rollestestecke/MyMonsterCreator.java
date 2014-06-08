package fh.tagmon.rollestestecke;

import java.util.ArrayList;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
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
		Stats stats = new Stats(0,30,30,30,111,100,10,123);
		
		//Attribute
		Attribut attribut = new Attribut(0, 10, 11, 12, 13);
		
		//Abilitys
		Ability beiﬂAttacke =  getBeiﬂAttacke();
		Ability block = getBlockAbility();
		Ability absorb = getAbsorbAbility();
		
		ArrayList<Ability>	abilityList = new ArrayList<Ability>();
		abilityList.add(beiﬂAttacke);
		abilityList.add(block);
		abilityList.add(absorb);
		
		//Kˆrperteile
		Koerperteil arm = new Koerperteil(0, "Koerpterteili", abilityList, KoerperteilArt.ARM, new AttributModifikator(0, 0, 0, 0));
		
		ArrayList<Koerperteil>koerperteilList = new ArrayList<Koerperteil>();
		koerperteilList.add(arm);
		
		//setze Monster zusammen
		Monster dummyMonster = new Monster(0, "MonsterName", attribut, koerperteilList, stats);
		
		return dummyMonster;
	}
	
	
	
	
	private Ability getBeiﬂAttacke(){
		Ability ability = new Ability("Beiﬂattacke", 20, AbilityTargetRestriction.ENEMY);
		IAbilityComponent component = new Damage(10, AbilityTargetRestriction.ENEMY);
		ability.addAbilityComponent(component);
		return ability;
	}
	
	private Ability getBlockAbility(){
		Ability blockAbility = new Ability("BLOCKABILITY", 11, AbilityTargetRestriction.SELF);
		IAbilityComponent buff = new Buff(2,0,5,0,null);
		blockAbility.addAbilityComponent(buff);
		return blockAbility;
	}
	
	private Ability getAbsorbAbility(){
		Ability absorbAbility = new Ability("Absorber", 10, AbilityTargetRestriction.SELF);
		IAbilityComponent schadensAbsorb = new Schadensabsorbation(3, 25, null);
		absorbAbility.addAbilityComponent(schadensAbsorb);
		return absorbAbility;
	}
}
