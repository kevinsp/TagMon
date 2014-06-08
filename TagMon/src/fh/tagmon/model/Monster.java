package fh.tagmon.model;

import java.util.ArrayList;
import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Ability;

public class Monster {
	
	private int id;
	private String name;
	
	private Attribut attribut;
	
	private ArrayList<Koerperteil> koerperteile;
	
	private Stats stats;
	
	
	// MonsterStuff that is not saved in DB
	private int additionalStrength = 0;
	private int additionalArmorValue= 0;
	private int additionalConstitution = 0;
	private int additionalIntelligence = 0;
	
	private final DurationAbilityListHandler durationAbilityListHandler = new DurationAbilityListHandler();
	private final DamageAbsorbationHelper dmgAbsHandler = new DamageAbsorbationHelper(this.durationAbilityListHandler);
	
	public Monster(int id, String name, Attribut attribut, ArrayList<Koerperteil> koerperteile, Stats stats){
		this.id = id;
		this.name = name;
		this.attribut = attribut;
		this.koerperteile = koerperteile;
		this.stats = stats;
	}
	
	public int getCurrentLifePoints(){
		return stats.getCurHP();
	}

    public int getMaxLifePoints(){
        return stats.getMaxHP();
    }
	
	public LinkedList<Ability> getAbilitys(){
		LinkedList<Ability> abilities = new LinkedList<Ability>();
		
		for (Koerperteil koerperteil : koerperteile) {
			for (Ability ability : koerperteil.getAbilityList()) {
				abilities.add(ability);
			}
		}
		return abilities;
	}
	
	
	
	public int getStrength(){
		int koerperteilStrength = 0;
		for (Koerperteil koerperteil : koerperteile) {
			koerperteilStrength += koerperteil.getAttributModifikator().getStaerke();
		}
		return (attribut.getStaerke() + additionalStrength + koerperteilStrength);
	}
	
	public int getArmorValue(){
		int koerperteilArmor = 0;
		for (Koerperteil koerperteil : koerperteile) {
			koerperteilArmor += koerperteil.getAttributModifikator().getVerteidigung();
		}
		return (attribut.getVerteidigung() + additionalArmorValue + koerperteilArmor);
	}
	
	public int getIntelligence(){
		int koerperteilIntelligence = 0;
		for (Koerperteil koerperteil : koerperteile) {
			koerperteilIntelligence += koerperteil.getAttributModifikator().getIntelligenz();
		}
		return (attribut.getIntelligenz() + additionalIntelligence + koerperteilIntelligence);
	}
	
	public int getConstitution(){
		int koerperteilConstitution = 0;
		for (Koerperteil koerperteil : koerperteile) {
			koerperteilConstitution += koerperteil.getAttributModifikator().getKonstitution();
		}
		return (attribut.getKonstitution() + additionalConstitution + koerperteilConstitution);
	}
	

	public int decreaseLifePoints(int decreaseValue){
		if(decreaseValue >= stats.getCurHP()){
			stats.setCurHP(0);
		}
		else{
			stats.setCurHP(stats.getCurHP()-decreaseValue);
		}	
		return stats.getCurHP();
	}
	
	
	public void setAdditionalStrength(int additionalStrength) {
		this.additionalStrength = additionalStrength;
	}

	public void setAdditionalArmorValue(int additionalArmorValue) {
		this.additionalArmorValue = additionalArmorValue;
	}

	public void setAdditionalConstitution(int additionalConstitution) {
		this.additionalConstitution = additionalConstitution;
	}

	public void setAdditionalIntelligence(int additionalIntelligence) {
		this.additionalIntelligence = additionalIntelligence;
	}
	
	public DamageAbsorbationHelper getDmgAbsHelper() {
		return dmgAbsHandler;
	}


	public DurationAbilityListHandler getDurationAbilityListHandler(){
		return this.durationAbilityListHandler;
	}

	
	
}
