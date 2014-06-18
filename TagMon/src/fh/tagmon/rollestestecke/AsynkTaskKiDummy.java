package fh.tagmon.rollestestecke;


import fh.tagmon.model.Monster;
import android.os.AsyncTask;


public class AsynkTaskKiDummy extends AsyncTask<Void, Void, Void>{

	private Monster kiMonster;
	private String kiName;
	
	public AsynkTaskKiDummy(Monster kiMonster, String kiName){
		this.kiMonster = kiMonster;
		this.kiName = kiName;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		RollesTestKi ki = new RollesTestKi(kiName, kiMonster);
		ki.playTheGame();
		
		return null;
	}

}
