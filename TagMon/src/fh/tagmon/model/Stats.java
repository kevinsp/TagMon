package fh.tagmon.model;

public class Stats {
	
	private int id;
	private int maxHP;
	private int curHP;
	private int maxEP;
	private int curEP;
	private int regEP;
	private int lvl;
	private int curEXP;
	private int defense;

	
	public Stats(int id, int maxHP, int curHP, int maxEP, int curEP, int regEP, int lvl, int curEXP, int defense) {
		this.id = id;
		this.maxHP = maxHP;
		this.curHP = curHP;
		this.maxEP = maxEP;
		this.curEP = curEP;
		this.regEP = regEP;
		this.lvl = lvl;
		this.curEXP = curEXP;
		this.defense = defense;
	}
	
	
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getCurHP() {
		return curHP;
	}
	public void setCurHP(int curHP) {
		this.curHP = curHP;
	}
	public int getMaxEP() {
		return maxEP;
	}
	public void setMaxEP(int maxEP) {
		this.maxEP = maxEP;
	}
	public int getCurEP() {
		return curEP;
	}
	public void setCurEP(int curEP) {
		this.curEP = curEP;
	}
	public int getRegEP() {
		return regEP;
	}
	public void setRegEP(int regEP) {
		this.regEP = regEP;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getCurEXP() {
		return curEXP;
	}
	public void setDefense(int defense){
		this.defense = defense;
	}
	public int getDefensye(){
		return defense;
	}
	public void setCurEXP(int curEXP) {
		this.curEXP = curEXP;
	}
	public int getId() {
		return id;
	}

}
