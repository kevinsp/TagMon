package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;



public class Buff extends AbilityComponent implements IAbilityComponent,IDurationAbilityComponent {
	private static final long serialVersionUID = 1L;
	private int duration;
	private int strengthBuff = 0;
	private int armorValueBuff = 0;
	private int constitutionBuff = 0;
	
	public Buff(int duration, int strengthBuff, int armorValueBuff, int constitutionBuff, AbilityTargetRestriction abilityTargetRestriction){
		
		super(AbilityComponentTypes.BUFF, abilityTargetRestriction);
		
		this.duration = duration;
		this.strengthBuff = strengthBuff;
		this.constitutionBuff = constitutionBuff;
		this.armorValueBuff = armorValueBuff;

	}

	
	//brauch man nicht mehr sobalt über netzwerk die objecte transportiert werden
	@Override
	public Buff cloneMe(){
		return new Buff(this.duration, this.strengthBuff, this.armorValueBuff, this.constitutionBuff, this.componentTargetRestr);
	}
	
	public int getStrengthBuff() {
		return strengthBuff;
	}

	public void setStrengthBuff(int strengthBuff) {
		this.strengthBuff = strengthBuff;
	}


	public int getArmorValueBuff() {
		return armorValueBuff;
	}

	public void setArmorValueBuff(int armorValueBuff) {
		this.armorValueBuff = armorValueBuff;
	}

	public int getConstitutionBuff() {
		return constitutionBuff;
	}

	public void setConstitutionBuff(int constitutionBuff) {
		this.constitutionBuff = constitutionBuff;
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
