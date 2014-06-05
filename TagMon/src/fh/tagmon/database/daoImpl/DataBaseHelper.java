package fh.tagmon.database.daoImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.Heal;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.Stun;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Attribut;
import fh.tagmon.model.AttributModifikator;
import fh.tagmon.model.Buff;
import fh.tagmon.model.Faehigkeit;
import fh.tagmon.model.Koerperteil;
import fh.tagmon.model.KoerperteilArt;
import fh.tagmon.model.Stats;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/fh.tagmon/databases/";

	private static String DB_NAME = "db.sqlite3";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	
	
	public Attribut getAttribute(int monsterID){

		 
		String sqlQuery = 	   "SELECT tAttr.staerke, tAttr.intelligenz, tAttr.konstitution, tAttr.verteidigung " +
					 		   "FROM tagdb_monster tMonster INNER JOIN tagdb_attribute tAttr " +
					 		   "	ON tMonster.id = tAttr.monster_id " + 
				 			   "WHERE tMonster.id = " + monsterID;
		 
		 Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		 
		 ArrayList<Integer> attribList = new ArrayList<Integer>();
		 
		 while(cursor.moveToNext()){
			 for (int i=0; i<cursor.getColumnCount(); i++) {
				 attribList.add(cursor.getInt(i));
			}
		 }
		 return new Attribut(attribList.get(0), attribList.get(1), attribList.get(2), attribList.get(3), attribList.get(4));
	}
	
	
	public ArrayList<Koerperteil> getKoerperteile(int monsterID) throws MonsterDAOException{
	 
		 String sqlQuery = 	"SELECT tKM.koerperteile_id, tK.name, tK.art " +
				 			"FROM tagdb_monster tMonster " + 
				 			"INNER JOIN tagdb_koerperteile_monster tKM " +
				 				"ON tMonster.id = tKM.monster_id " +
				 			"INNER JOIN tagdb_koerperteile tK " +
				 				"ON tKM.koerperteile_id = tK.id " +
				 			"WHERE tMonster.id = " + monsterID;
		 
		 Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		 
		 int kId = 0;
		 String name = "";
		 int art = 0;
		 
		 ArrayList<Koerperteil> koerperteilList = new ArrayList<Koerperteil>();
		 
		 while(cursor.moveToNext()){
			 kId = cursor.getInt(0);
			 name = cursor.getString(1);
			 art = cursor.getInt(2);
			 
			 AttributModifikator attrMod = getAttributModifikator(kId);
			 ArrayList<Ability> abilityList = getAbilities(kId);
			 
			 KoerperteilArt koerperteileArt;
			 
			 // 1=Kopf, 2=Torso, 3=Arm, 4=Bein
			 switch (art) {
			case 1: koerperteileArt = KoerperteilArt.KOPF;
				break;
			case 2: koerperteileArt = KoerperteilArt.TORSO;
				break;
			case 3: koerperteileArt = KoerperteilArt.ARM;
				break;
			case 4: koerperteileArt = KoerperteilArt.BEIN;
				break;
			default: throw new MonsterDAOException();
			}
			 koerperteilList.add(new Koerperteil(kId, name, abilityList, koerperteileArt, attrMod));
		 }
		 return koerperteilList;
	}
	
	
	public AttributModifikator getAttributModifikator(int koerperteileID){
		
		 String sqlQuery = 	"SELECT tAttrM.staerke, tAttrM.intelligenz, tAttrM.konstitution, tAttrM.verteidigung " +
							"FROM tagdb_attributmodifikator tAttrM " +
							"WHERE tAttrM.koerperteile_id = " + koerperteileID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		
		ArrayList<Integer> attribList = new ArrayList<Integer>();
		 
		 while(cursor.moveToNext()){
			 for (int i=0; i<cursor.getColumnCount(); i++) {
				 attribList.add(cursor.getInt(i));
			}
		 }
		 
		 return new AttributModifikator(attribList.get(0), attribList.get(1), attribList.get(2), attribList.get(3));	
	}
	
	
	public ArrayList<Ability> getAbilities(int koerperteileID){
		
		 String sqlQuery = 	"SELECT tFaehigkeit.id, tFaehigkeit.name, tFaehigkeit.ziel, tFaehigkeit.energiekosten, tFaehigkeit.angriffsziel, tFaehigkeit.angriffswert, " +
				 					"tFaehigkeit.heilungsziel, tFaehigkeit.heilungswert, tFaehigkeit.stunziel, tFaehigkeit.stundauer, " +
				 					"tFaehigkeit.absorbationsziel, tFaehigkeit.schadensabsorbation " +
				 			"FROM tagdb_faehigkeit tFaehigkeit " + 
				 			"INNER JOIN tagdb_faehigkeit_koerperteile tFK " +
				 				"ON tFaehigkeit.id = tFK.faehigkeit_id " +
				 			"INNER JOIN tagdb_faehigkeit tF " +
				 				"ON tFK.koerperteile_id = tF.id " +
				 			"WHERE tFK.koerperteile_id = " + koerperteileID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		ArrayList<Ability> abilityList = new ArrayList<Ability>();
		
		int id;
		String name;
		int ziel;
		int energiekosten;
		int angriffsziel;
		int angriffswert;
		int heilungsziel;
		int heilungswert;
		int stunziel;
		int stundauer;
		int absorbationziel;
		int schadensabsorbation;
		
		while(cursor.moveToNext()){
			
			LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();
			
			id = cursor.getInt(0);
			name = cursor.getString(1);
			ziel = cursor.getInt(0);
			energiekosten = cursor.getInt(2);
			angriffsziel = cursor.getInt(3);
			angriffswert = cursor.getInt(4);
			heilungsziel = cursor.getInt(2);
			heilungswert = cursor.getInt(5);
			stunziel = cursor.getInt(0);
			stundauer = cursor.getInt(6);
			absorbationziel = cursor.getInt(0);
			schadensabsorbation = cursor.getInt(7);
			
			if(angriffswert!=0){
				abilityComponents.add(new Damage(angriffswert, getAbilityTargetRestriction(angriffsziel)));
			}
			
			if(heilungswert!=0){
				abilityComponents.add(new Heal(heilungswert, getAbilityTargetRestriction(heilungsziel)));
			}
			
			if(stundauer!=0){
				abilityComponents.add(new Stun(stundauer, getAbilityTargetRestriction(stunziel)));
			}
			
			if(schadensabsorbation!=0){
				// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				// NOOOOOOOOOOOOOOOOOOOOOOOCH MAAAAAAAAAAAAAAAAAAACHEN
				// NEW DAMAGE SHIT NOCH MACHEN DAMAGEABSORBATION IST NOCH KEINE KOMPONENTE WIRD ABER NOCH GEMACHT!!!!!!!!!1
				//abilityComponents.add(new DamageSHIT, getAbilities(schadensabsorbation));
			}
			
			abilityList.add(new Ability(name, energiekosten, getAbilityTargetRestriction(ziel)));
			
		}
		return abilityList;
	}
	
	private AbilityTargetRestriction getAbilityTargetRestriction(int ziel){
		AbilityTargetRestriction abilityTargetRest;
		// targetRestriction 1=self, 2=enemy, 3=selfANDenemy, 4=enemygroup, 5=owngroup, 6=selfANDenemygroup, 7=owngroupANDenemy
		switch (ziel) {
		case 1: abilityTargetRest = AbilityTargetRestriction.SELF;
			break;
		case 2: abilityTargetRest = AbilityTargetRestriction.ENEMY;
				break;
		case 3: abilityTargetRest = AbilityTargetRestriction.SELFANDENEMY;
				break;
		case 4: abilityTargetRest = AbilityTargetRestriction.ENEMYGROUP;
				break;
		case 5: abilityTargetRest = AbilityTargetRestriction.OWNGROUP;
				break;
		case 6: abilityTargetRest = AbilityTargetRestriction.SELFANDENEMYGROUP;
				break;
		case 7: abilityTargetRest = AbilityTargetRestriction.OWNGROUPANDENEMY;
				break;
		default: abilityTargetRest = AbilityTargetRestriction.DEFAULT;
			break;
		}
		return abilityTargetRest;
	}
	
	
	public ArrayList<Buff> getBuff(int faehigkeitID){
		
		 String sqlQuery = 	"SELECT tBuff.id, tBuff.dauer, tBuff.ziel, tBuff.bStaerke, " +
							"		tBuff.bIntelligenz, tBuff.bKonstitution, tBuff.bVerteidigung " +
							"FROM tagdb_buff tBuff " +
							"WHERE tBuff.faehigkeit_id = " + faehigkeitID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		
		ArrayList<Buff> buffList = new ArrayList<Buff>();
		
		while(cursor.moveToNext()){
			buffList.add(new Buff(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6)));
		}
		return buffList;
		
	}
	
	
	public Stats getStats(int monsterID){
		
		 String sqlQuery = 	"SELECT tagdb_stats.maxHP,tagdb_stats.curHP,tagdb_stats.curHP,tagdb_stats.maxEP, " +
				 			"		tagdb_stats.curEP,tagdb_stats.regEP, tagdb_stats.lvl, tagdb_stats.curEXP " +
				 			"FROM tagdb_stats " +
				 			"WHERE tagdb_stats.monster_id = " + monsterID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);
		
		return new Stats(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));			

	}
	

	
	
	
	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

}