package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;



public class Buff extends AbilityComponent implements IAbilityComponent,IDurationAbilityComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2850820672053585639L;
	private int id;
	private int duration;
	private int strengthBuff = 0;
	private int intelligenceBuff = 0;
	private int constitutionBuff = 0;
	

	
	public Buff(int id, int duration, AbilityTargetRestriction abilityTargetRestriction, int strengthBuff, int intelligenceBuff, int constitutionBuff){
		super(AbilityComponentTypes.BUFF, abilityTargetRestriction);
		this.id = id;
		this.duration = duration;
		this.strengthBuff = strengthBuff;
		this.intelligenceBuff = intelligenceBuff;
		this.constitutionBuff = constitutionBuff;
	}

	
	//brauch man nicht mehr sobalt ber netzwerk die objecte transportiert werden
	@Override
	public Buff cloneMe(){
		return new Buff(this.id, this.duration, this.componentTargetRestr, this.strengthBuff, this.constitutionBuff, this.intelligenceBuff );
	}
	
	public int getStrengthBuff() {
		return strengthBuff;
	}

	public void setStrengthBuff(int strengthBuff) {
		this.strengthBuff = strengthBuff;
	}
	
	public int getIntelligenceBuff(){
		return this.intelligenceBuff;
	}
	
	public void setIntelligenceBuff(int intelligenceBuff){
		this.intelligenceBuff = intelligenceBuff;
	}

	public int getConstitutionBuff() {
		return constitutionBuff;
	}

	public void setConstitutionBuff(int constitutionBuff) {
		this.constitutionBuff = constitutionBuff;
	}
	
	public int getID(){
		return this.id;
	}

	@Override
	public int getDuration(){
		return this.duration;
	}
	
	

	@Override
	public AbilityComponentTypes getComponentType() {
		return this.componentType;
	}

	@Override
	public void setReqStats(Monster monster) {
		
	}


	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}


}
