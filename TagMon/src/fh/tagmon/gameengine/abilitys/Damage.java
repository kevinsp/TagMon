package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.choseability.AbilityTargetRestriction;

import fh.tagmon.gameengine.MonsterDummys.Monster;

public class Damage extends AbilityComponent implements IAbilityComponent {

	private int baseDmg;
	
	private int monsterStrength = 0;
	private int monsterAttackPower = 0;
	
	public Damage(int baseDmg){
		super(AbilityComponentTypes.DAMAGE);
		this.baseDmg = baseDmg;
	}
	
	
	public int getDamage(){
		return (this.getMonsterStrength() + this.getMonsterAttackPower() + this.baseDmg);
	}


	public int getMonsterStrength() {
		return monsterStrength;
	}


	public void setMonsterStrength(int monsterStrength) {
		this.monsterStrength = monsterStrength;
	}


	public int getMonsterAttackPower() {
		return monsterAttackPower;
	}


	public void setMonsterAttackPower(int monsterAttackPower) {
		this.monsterAttackPower = monsterAttackPower;
	}


	@Override
	public AbilityComponentTypes getComponentType() {
		return this.componentType;
	}


	@Override
	public void setReqStats(Monster monster) {
		this.setMonsterStrength(monster.getStrength());
		this.setMonsterAttackPower(monster.getAttackPower());
	}


	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}
	
}
