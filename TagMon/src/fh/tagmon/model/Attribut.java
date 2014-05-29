package fh.tagmon.model;

public class Attribut {
	
	private int id;
	private int staerke;
	private int intelligenz;
	private int konstitution;
	private int verteidigung;
	
	
	public Attribut(int id, int staerke, int intelligenz, int konstitution, int verteidigung){
		this.id = id;
		this.staerke = staerke;
		this.intelligenz = intelligenz;
		this.konstitution = konstitution;
		this.verteidigung = verteidigung;
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
	public int getVerteidigung() {
		return verteidigung;
	}
	public void setVerteidigung(int verteidigung) {
		this.verteidigung = verteidigung;
	}
	public int getId() {
		return id;
	}
	

}
