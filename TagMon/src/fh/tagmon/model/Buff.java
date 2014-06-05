package fh.tagmon.model;

public class Buff {
	

	public Buff(int id, int dauer, int ziel, int bStaerke, int bINtelligenz, int bKonstitution, int bVerteidigung) {
		this.id = id;
		this.dauer = dauer;
		this.ziel = ziel;
		this.bStaerke = bStaerke;
		this.bIntelligenz = bINtelligenz;
		this.bKonstitution = bKonstitution;
		this.bVerteidigung = bVerteidigung;
	}

	private int id;
	private int dauer;
	
	// create enum class later -> like 0=self, 1=enemy, 2=enemyGroup ...
	private int ziel;
	
	private int bStaerke;
	private int bIntelligenz;
	private int bKonstitution;
	private int bVerteidigung;

}
