package fh.tagmon.model;

import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.*;

public class DamageAbsorbationHelper {

	private DurationAbilityListHandler durationAbilityListHandler;
	
	
	public DamageAbsorbationHelper(DurationAbilityListHandler durationAbilityListHandler){
		this.durationAbilityListHandler = durationAbilityListHandler;
	}
	

	public void addDamageAbsorbationComponant(Schadensabsorbation schadensabsorbation){
		this.durationAbilityListHandler.addDurationAbilityListElement(schadensabsorbation);
	}
	
	
	private LinkedList<Schadensabsorbation> findActiveDamageAbsorbationBuffs(){
		LinkedList<Schadensabsorbation> activeDamageAbsorbationBuffs = new LinkedList<Schadensabsorbation>();
		
		for(DurationAbilityListElement durationAbilityListElement : this.durationAbilityListHandler.getDurationAbilityList()){
			IDurationAbilityComponent durationAbilityComponent = durationAbilityListElement.getDurationAbilityComponent();
			IAbilityComponent abilityComponent = (IAbilityComponent) durationAbilityComponent;
			
			if( abilityComponent.getComponentType() == AbilityComponentTypes.SCHADENSABSORBATION){
				Schadensabsorbation schadenAbs = (Schadensabsorbation) abilityComponent;
				if(schadenAbs.getAbsorbationAmount() > 0){
					activeDamageAbsorbationBuffs.addLast(schadenAbs);
				}
			}
			
		}
		return activeDamageAbsorbationBuffs;
	}
	
	public int getDamageAbsorbationAmount(){
		LinkedList<Schadensabsorbation> activeDamageAbsorbationBuffs = this.findActiveDamageAbsorbationBuffs();
		
		int dmgAbsorbAmount = 0;
		
		for(Schadensabsorbation schadensabsorbation : activeDamageAbsorbationBuffs){
			dmgAbsorbAmount += schadensabsorbation.getAbsorbationAmount();
		}
		
		return dmgAbsorbAmount;
	}
	
	public int doAbsorbation(int dmg){
		LinkedList<Schadensabsorbation> activeDamageAbsorbationBuffs = this.findActiveDamageAbsorbationBuffs();
		
		int dmgToReduce = dmg;
		
		for(Schadensabsorbation schadensabsorbation : activeDamageAbsorbationBuffs){
			int dmgAbsorbAmount = schadensabsorbation.getAbsorbationAmount();
		
			if(dmgToReduce > dmgAbsorbAmount){
				dmgToReduce -= dmgAbsorbAmount;
				schadensabsorbation.setAbsorbationAmount(0);
			}
			else{
				dmgAbsorbAmount -= dmgToReduce;
				schadensabsorbation.setAbsorbationAmount(dmgAbsorbAmount);
				dmgToReduce = 0;
			}
		}
		return dmgToReduce;
	}
	
}
