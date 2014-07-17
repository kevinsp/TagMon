/**
 * Author: Christian Roletscheck
 */


package fh.tagmon.gameengine.player.choseability;


import java.util.HashMap;
import java.util.LinkedList;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.model.Monster;

public class AbilityChooser{
	private final HashMap<Integer, Ability> monsterAbilitys = new HashMap<Integer, Ability>();
	private int abilityCounter = 0;
	
	public AbilityChooser(Monster newMonster){
		this.generateHashMap(newMonster.getAbilitys());		
	}
		
	public Ability getAbility(int pos){
		Ability abi = this.monsterAbilitys.get(pos);
		return abi;
	}
	
	private void generateHashMap(LinkedList<Ability> list){
		for(Ability abil: list){
			this.monsterAbilitys.put(abilityCounter, abil);
			this.abilityCounter++;
		}
	}
	
	public HashMap<Integer, Ability> getAllAbilitys(){
		return this.monsterAbilitys;
	}
	

}
