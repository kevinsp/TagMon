package fh.tagmon.gameengine.MonsterDummys;

import java.util.LinkedList;

import android.util.Log;
import fh.tagmon.gameengine.abilitys.Buff;

public class DamageAbsorbationHandler {

	private BuffListHandler buffListHandler;
	
	
	public DamageAbsorbationHandler(BuffListHandler buffListHandler){
		this.buffListHandler = buffListHandler;
	}
	
	
	private LinkedList<BuffListElement> findActiveDamageAbsorbationBuffs(){
		LinkedList<BuffListElement> activeDamageAbsorbationBuffs = new LinkedList<BuffListElement>();
		
		for(BuffListElement buffListElement : this.buffListHandler.getBuffList()){
			Buff leBuff = buffListElement.getBuff();
			if(leBuff.getDamageAbsorbationAmount() > 0){
				activeDamageAbsorbationBuffs.addLast(buffListElement);
			}
		}
		
		return activeDamageAbsorbationBuffs;
	}
	
	public int getDamageAbsorbationAmount(){
		LinkedList<BuffListElement> activeDamageAbsorbationBuffs = this.findActiveDamageAbsorbationBuffs();
		int dmgAbsorbAmount = 0;
		for(BuffListElement buffListElement : activeDamageAbsorbationBuffs){
			Buff leBuff = buffListElement.getBuff();
			dmgAbsorbAmount += leBuff.getDamageAbsorbationAmount();
		}
		return dmgAbsorbAmount;
	}
	
	public int doAbsorbation(int dmg){
		LinkedList<BuffListElement> activeDamageAbsorbationBuffs = this.findActiveDamageAbsorbationBuffs();
		int dmgToReduce = dmg;
		for(BuffListElement buffListElement : activeDamageAbsorbationBuffs){
			Buff leBuff = buffListElement.getBuff();
			int dmgAbsorbAmount = leBuff.getDamageAbsorbationAmount();
		
			if(dmgToReduce > dmgAbsorbAmount){
				dmgToReduce -= dmgAbsorbAmount;
				leBuff.setDamageAbsorbationAmount(0);
			}
			else{
				dmgAbsorbAmount -= dmgToReduce;
				leBuff.setDamageAbsorbationAmount(dmgAbsorbAmount);
				dmgToReduce = 0;
			}
		}
		return dmgToReduce;
	}
	
}
