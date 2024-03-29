/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.abilitys;



import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;



public class Damage extends AbilityComponent implements IAbilityComponent {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6957715094977922266L;

	private int baseDmg;
	
	private int monsterStrength = 0;
	
	public Damage(int baseDmg, AbilityTargetRestriction abilityTargetRestriction){
		super(AbilityComponentTypes.DAMAGE, abilityTargetRestriction);
		this.baseDmg = baseDmg;
	}
	
	
	public int getDamage(){
		return (this.getMonsterStrength() + this.baseDmg);
	}


	public int getMonsterStrength() {
		return monsterStrength;
	}


	public void setMonsterStrength(int monsterStrength) {
		this.monsterStrength = monsterStrength;
	}

	@Override
	public AbilityComponentTypes getComponentType() {
		return this.componentType;
	}


	@Override
	public void setReqStats(Monster monster) {
		this.setMonsterStrength(monster.getStrength());
		
	}


	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}


	
}
