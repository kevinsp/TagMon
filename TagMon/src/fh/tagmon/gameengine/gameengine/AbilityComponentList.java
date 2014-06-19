package fh.tagmon.gameengine.gameengine;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import fh.tagmon.gameengine.abilitys.IAbilityComponent;

public class AbilityComponentList implements Serializable{
	private static final long serialVersionUID = 1L;
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
