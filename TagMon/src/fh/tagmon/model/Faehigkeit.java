package fh.tagmon.model;

import java.util.ArrayList;

public class Faehigkeit {
	
	
	public Faehigkeit(int id, String name, int energieKosten, int ziel, int angriffswert, int heilungswert, int stundauer, int schadensabsorbation, ArrayList<Buff> buffs) {
		this.id = id;
		this.name = name;
		this.energieKosten = energieKosten;
		this.ziel = ziel;
		this.angriffswert = angriffswert;
		this.heilungswert = heilungswert;
		this.stundauer = stundauer;
		this.schadensabsorbation = schadensabsorbation;
		this.buffs = buffs;
	}

	private int id;
	private String name;
	private int energieKosten;
	private int ziel;
	private int angriffswert;
	private int heilungswert;
	private int stundauer;
	private int schadensabsorbation;
	
	private ArrayList<Buff> buffs;

}
