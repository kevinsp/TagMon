package fh.tagmon.model;

import java.util.ArrayList;

public class Koerperteil {
	
	private int id;
	private String name;
	
	private ArrayList<Faehigkeit> faehigkeiten;
	
	//  art -> 0=Kopf, 1=Torso, 2=Arme etc. -> create enum class later maybe with kopf,torso etc
	private int art;
	
	private AttributModifikator attributModifikator;

}
