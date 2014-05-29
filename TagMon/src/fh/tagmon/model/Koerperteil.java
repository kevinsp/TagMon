package fh.tagmon.model;

import java.util.ArrayList;

import fh.tagmon.gameengine.abilitys.Ability;

public class Koerperteil {
	
	private int id;
	private String name;
	
	private ArrayList<Ability> abilityList;
	//private ArrayList<Faehigkeit> faehigkeiten;
	
	private KoerperteilArt koerperteilArt;


	private AttributModifikator attributModifikator;
	
	public ArrayList<Ability> getAbilityList(){
		return abilityList;
	}
	
	public AttributModifikator getAttributModifikator() {
		return attributModifikator;
	}
	
	public Koerperteil(int id, String name, ArrayList<Ability> abilityList, KoerperteilArt koerperteilArt, AttributModifikator attributModifikator){
		this.id = id;
		this.name = name;
		this.abilityList = abilityList;
		this.koerperteilArt = koerperteilArt;
		this.attributModifikator = attributModifikator;
		
	}

}
