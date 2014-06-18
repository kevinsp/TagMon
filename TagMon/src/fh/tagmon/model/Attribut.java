package fh.tagmon.model;

public class Attribut {
	
	private int id;
	private int staerke;
	private int intelligenz;
	private int konstitution;
	
	
	public Attribut(int id, int staerke, int intelligenz, int konstitution){
		this.id = id;
		this.staerke = staerke;
		this.intelligenz = intelligenz;
		this.konstitution = konstitution;
	}
	
	
	public int getStaerke() {
		return staerke;
	}
	public void setStaerke(int staerke) {
		this.staerke = staerke;
	}
	public int getIntelligenz() {
		return intelligenz;
	}
	public void setIntelligenz(int intelligenz) {
		this.intelligenz = intelligenz;
	}
	public int getKonstitution() {
		return konstitution;
	}
	public void setKonstitution(int konstitution) {
		this.konstitution = konstitution;
	}
	public int getId() {
		return id;
	}
	

}
