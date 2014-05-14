package fh.tagmon.gameengine.MonsterDummys;


import java.util.LinkedList;






import fh.tagmon.gameengine.abilitys.Ability;


public class Monster {

	private String monsterName;
	
	private int maxLifePoints;
	private int currentLifePoints;
	private int strength;
	private int attackPower;
	private int armorValue;
	private int constitution;
	
	
	private int additionalStrength = 0;
	private int additionalAttackPower= 0;
	private int additionalArmorValue= 0;
	private int additionalConstitution = 0;
	private int damageAbsorbationPool = 0;
	
	private final LinkedList<Ability> abilityHolder = new LinkedList<Ability>();
	
	private final BuffListHandler buffListHandler = new BuffListHandler();
	private final DamageAbsorbationHandler dmgAbsHandler = new DamageAbsorbationHandler(this.buffListHandler);
	
	public Monster(String name, int lifePoints, int strength, int attackPower, int ArmorValue){
		this.monsterName = name;
		this.maxLifePoints = lifePoints;
		this.currentLifePoints = lifePoints;
		this.strength = strength;
		this.attackPower = attackPower;
		this.armorValue = ArmorValue;

		
	}

	
	public int getCurrentLifePoints(){
		return this.currentLifePoints;
	}
	
	public void addAbility(Ability newAbility){
		this.abilityHolder.add(newAbility);
	}

	public LinkedList<Ability> getAbilitys(){
		return this.abilityHolder;
	}
	
	public int getAttackPower(){
		return (this.getAdditionalAttackPower() + this.attackPower);
	}
	
	
	public int getStrength(){
		return (this.strength + this.getAdditionalStrength());
	}
	
	public int getArmorValue(){
		return (this.armorValue + this.getAdditionalArmorValue());
	}
	
	public int getDamageAbsorbationPool(){
		return this.damageAbsorbationPool;
	}
	
	public void setDamageAbsorbationPool(int newPool){
		this.damageAbsorbationPool = newPool;
	}
	
	public int decreaseLifePoints(int decreaseValue){
		if(decreaseValue >= this.currentLifePoints){
			this.currentLifePoints = 0;
		}
		else{
			this.currentLifePoints -= decreaseValue;
		}	
		return this.currentLifePoints;
	}


	public int getAdditionalStrength() {
		return additionalStrength;
	}


	public void setAdditionalStrength(int additionalStrength) {
		this.additionalStrength = additionalStrength;
	}


	public int getAdditionalAttackPower() {
		return additionalAttackPower;
	}


	public void setAdditionalAttackPower(int additionalAttackPower) {
		this.additionalAttackPower = additionalAttackPower;
	}


	public int getAdditionalArmorValue() {
		return additionalArmorValue;
	}


	public void setAdditionalArmorValue(int additionalArmorValue) {
		this.additionalArmorValue = additionalArmorValue;
	}


	public int getConstitution() {
		return constitution;
	}


	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}


	public int getAdditionalConstitution() {
		return additionalConstitution;
	}


	public void setAdditionalConstitution(int additionalConstitution) {
		this.additionalConstitution = additionalConstitution;
	}


	public DamageAbsorbationHandler getDmgAbsHandler() {
		return dmgAbsHandler;
	}


	public BuffListHandler getBuffListHandler(){
		return this.buffListHandler;
	}
	
}
