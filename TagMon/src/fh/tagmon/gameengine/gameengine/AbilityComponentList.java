package fh.tagmon.gameengine.gameengine;

import java.util.LinkedList;
import java.util.List;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;

public class AbilityComponentList{
    private List<IAbilityComponent> abiComp = new LinkedList<IAbilityComponent>();
    final int target;
    	
    public AbilityComponentList(int target){
    	this.target = target;
    }
    	
    public void addAbilityCommponent(IAbilityComponent ac){
    	abiComp.add(ac);
    }
    
    public List<IAbilityComponent> getAbilityList(){
    	List<IAbilityComponent> abilityComponentCloneList = new LinkedList<IAbilityComponent>();
    	abilityComponentCloneList.addAll(abiComp);
    	return abilityComponentCloneList;
    }
}
