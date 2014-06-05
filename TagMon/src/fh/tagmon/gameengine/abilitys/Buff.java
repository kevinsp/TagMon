package fh.tagmon.gameengine.abilitys;

import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;



public class Buff extends AbilityComponent implements IAbilityComponent {

	private int duration;
	private int strengthBuff = 0;
	private int armorValueBuff = 0;
	private int constitutionBuff = 0;
	private int damageAbsorbationAmount = 0;
	
	public Buff(int duration, int strengthBuff, int armorValueBuff, int constitutionBuff, int damageAbsorbationAmount, AbilityTargetRestriction abilityTargetRestriction){
		
		super(AbilityComponentTypes.BUFF, abilityTargetRestriction);
		
		this.duration = duration;
		this.strengthBuff = strengthBuff;
		this.constitutionBuff = constitutionBuff;
		this.armorValueBuff = armorValueBuff;
		this.setDamageAbsorbationAmount(damageAbsorbationAmount);
	}

	public Buff clone(){
		return new Buff(this.duration, this.strengthBuff, this.armorValueBuff, this.constitutionBuff, this.damageAbsorbationAmount,this.componentTargetRestr);
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

	public int getDamageAbsorbationAmount() {
		return damageAbsorbationAmount;
	}

	public void setDamageAbsorbationAmount(int damageAbsorbationAmount) {
		this.damageAbsorbationAmount = damageAbsorbationAmount;
	}

	@Override
	public AbilityTargetRestriction getComponentTargetRestriction() {
		return this.componentTargetRestr;
	}


}
