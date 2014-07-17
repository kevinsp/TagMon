package fh.tagmon.database.daoImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.abilitys.*;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.*;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Die {@link DataBaseHelperLocal} wird für die Datenbank verwendet, in der sich alle gegnerischen Monster befinden.
 * Diese Datenbank wird über die Tagmon.de Admin Page erstellt und dann in die App synchronisiert.
 * 
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	/** Der Standard Pfad zur Anwendunsgdatenbank */
	private static String DB_PATH = "/data/data/fh.tagmon/databases/";

	/** Der Name der Datenbank */
	private static String DB_NAME = "db.sqlite3";

	/** Die {@link SQLiteDatabase} */
	private SQLiteDatabase myDataBase;

	/** Der App {@link Context} */
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
		// boolean dbExist = false;

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
					SQLiteDatabase.NO_LOCALIZED_COLLATORS
							| SQLiteDatabase.OPEN_READONLY);
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

	/**
	 * Holt das {@link Attribut} des {@link Monster}s aus der Datenbank
	 * @param monsterID Die ID des Monsters
	 * @return Das {@link Attribut} Objekt des {@link Monster}s
	 * @throws MonsterDAOException
	 */
	public Attribut getAttribute(int monsterID) throws MonsterDAOException {

		String sqlQuery = "SELECT tAttr.id, tAttr.staerke, tAttr.intelligenz, tAttr.konstitution "
				+ "FROM tagdb_monster tMonster INNER JOIN tagdb_attribute tAttr "
				+ "	ON tMonster.id = tAttr.monster_id "
				+ "WHERE tMonster.id = " + monsterID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		ArrayList<Integer> attribList = new ArrayList<Integer>();

		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				attribList.add(cursor.getInt(i));
			}
		} else
			throw new MonsterDAOException(
					"Monster could not be created cause no attributes were found");

		return new Attribut(attribList.get(0), attribList.get(1),
				attribList.get(2), attribList.get(3));
	}

	/**
	 * Holt die {@link ArrayList} an {@link Koerperteil}en von einem {@link Monster} anhand dessen ID
	 * 
	 * @param monsterID Die {@link Integer} des {@link Monster}s
	 * @return {@link ArrayList} aus {@link Koerperteil}en
	 * @throws MonsterDAOException
	 */
	public ArrayList<Koerperteil> getKoerperteile(int monsterID)
			throws MonsterDAOException {

		String sqlQuery = "SELECT tKM.koerperteile_id, tK.name, tK.art "
				+ "FROM tagdb_monster tMonster "
				+ "INNER JOIN tagdb_koerperteile_monster tKM "
				+ "ON tMonster.id = tKM.monster_id "
				+ "INNER JOIN tagdb_koerperteile tK "
				+ "ON tKM.koerperteile_id = tK.id " + "WHERE tMonster.id = "
				+ monsterID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		int kId = 0;
		String name = "";
		int art = 0;

		ArrayList<Koerperteil> koerperteilList = new ArrayList<Koerperteil>();

		while (cursor.moveToNext()) {
			kId = cursor.getInt(0);
			name = cursor.getString(1);
			art = cursor.getInt(2);

			AttributModifikator attrMod = getAttributModifikator(kId);
			ArrayList<Ability> abilityList = getAbilities(kId);

			KoerperteilArt koerperteileArt;

			// 1=Kopf, 2=Torso, 3=Arm, 4=Bein
			switch (art) {
			case 1:
				koerperteileArt = KoerperteilArt.KOPF;
				break;
			case 2:
				koerperteileArt = KoerperteilArt.TORSO;
				break;
			case 3:
				koerperteileArt = KoerperteilArt.ARM;
				break;
			case 4:
				koerperteileArt = KoerperteilArt.BEIN;
				break;
			default:
				throw new MonsterDAOException(
						"The Monster's 'koerperteil' has no correct 'koerperteileArt'");
			}
			koerperteilList.add(new Koerperteil(kId, name, abilityList,
					koerperteileArt, attrMod));
		}

		if (koerperteilList.isEmpty())
			throw new MonsterDAOException(
					"Monster has not a single 'koerperteil'");

		return koerperteilList;
	}

	/**
	 * Holt den {@link AttributModifikator} für ein {@link Koerperteil} aus der Datenbank
	 * @param koerperteileID Die {@link Integer} ID des {@link Koerperteil}s für das der {@link AttributModifikator} geholt werden soll
	 * @return Der {@link AttributModifikator}
	 * @throws MonsterDAOException
	 */
	public AttributModifikator getAttributModifikator(int koerperteileID)
			throws MonsterDAOException {

		String sqlQuery = "SELECT tAttrM.staerke, tAttrM.intelligenz, tAttrM.konstitution "
				+ "FROM tagdb_attributmodifikator tAttrM "
				+ "WHERE tAttrM.koerperteile_id = " + koerperteileID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		ArrayList<Integer> attribList = new ArrayList<Integer>();

		while (cursor.moveToNext()) {
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				attribList.add(cursor.getInt(i));
			}
		}

		if (attribList.isEmpty())
			throw new MonsterDAOException(
					"Koerperteil has no 'attributModifikator'");

		return new AttributModifikator(attribList.get(0), attribList.get(1),
				attribList.get(2));
	}

	/**
	 * Holt eine {@link ArrayList} an {@link Ability}s für ein bestimmtes {@link Koerperteil} aus der DB
	 * @param koerperteileID Die {@link Integer} ID des {@link Koerperteil}s
	 * @return {@link ArrayList} aus {@link Ability}s
	 * @throws MonsterDAOException
	 */
	public ArrayList<Ability> getAbilities(int koerperteileID)
			throws MonsterDAOException {

		String sqlQuery = "SELECT tFaehigkeit.id, tFaehigkeit.name, tFaehigkeit.ziel, tFaehigkeit.energiekosten, tFaehigkeit.angriffsziel, tFaehigkeit.angriffswert, "
				+ "tFaehigkeit.heilungsziel, tFaehigkeit.heilungswert, tFaehigkeit.stunziel, tFaehigkeit.stundauer, "
				+ "tFaehigkeit.absorbationsziel, tFaehigkeit.absorbationsdauer, tFaehigkeit.schadensabsorbation, tFaehigkeit.cooldown "
				+ "FROM tagdb_faehigkeit tFaehigkeit "
				+ "INNER JOIN tagdb_faehigkeit_koerperteile tFK "
				+ "ON tFaehigkeit.id = tFK.faehigkeit_id "
				+ "INNER JOIN tagdb_faehigkeit tF "
				+ "ON tFK.koerperteile_id = tF.id "
				+ "WHERE tFK.koerperteile_id = " + koerperteileID;

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
		int absorbationsdauer;
		int schadensabsorbation;
		int cooldown;

		while (cursor.moveToNext()) {

			LinkedList<IAbilityComponent> abilityComponents = new LinkedList<IAbilityComponent>();

			id = cursor.getInt(0);
			name = cursor.getString(1);
			ziel = cursor.getInt(2);
			energiekosten = cursor.getInt(3);
			angriffsziel = cursor.getInt(4);
			angriffswert = cursor.getInt(5);
			heilungsziel = cursor.getInt(6);
			heilungswert = cursor.getInt(7);
			stunziel = cursor.getInt(8);
			stundauer = cursor.getInt(9);
			absorbationziel = cursor.getInt(10);
			absorbationsdauer = cursor.getInt(11);
			schadensabsorbation = cursor.getInt(12);
			cooldown = cursor.getInt(13);

			if (angriffswert != 0) {
				abilityComponents.add(new Damage(angriffswert,
						getAbilityTargetRestriction(angriffsziel)));
			}

			if (heilungswert != 0) {
				abilityComponents.add(new Heal(heilungswert,
						getAbilityTargetRestriction(heilungsziel)));
			}

			if (stundauer != 0) {
				abilityComponents.add(new Stun(stundauer,
						getAbilityTargetRestriction(stunziel)));
			}

			if (schadensabsorbation != 0) {
				abilityComponents.add(new Schadensabsorbation(
						absorbationsdauer, schadensabsorbation,
						getAbilityTargetRestriction(absorbationziel)));
			}

			// get Buffs for the ability
			ArrayList<fh.tagmon.gameengine.abilitys.Buff> buffList = getBuff(id);

			for (fh.tagmon.gameengine.abilitys.Buff buff : buffList) {
				abilityComponents.add(buff);
			}

			abilityList.add(new Ability(id, name, energiekosten, cooldown,
					getAbilityTargetRestriction(ziel), abilityComponents));

			if (abilityList.isEmpty())
				throw new MonsterDAOException("Empty ability list");

		}
		return abilityList;
	}

	/**
	 * Holt die {@link AbilityTargetRestriction} anhand eines Ziel {@link Integer}
	 * @param ziel Der {@link Integer} Wert des Ziels
	 * @return Die {@link AbilityTargetRestriction}, welche aus dem Ziel {@link Integer} umgewandelt wird
	 */
	private AbilityTargetRestriction getAbilityTargetRestriction(int ziel) {
		AbilityTargetRestriction abilityTargetRest;
		// targetRestriction 1=self, 2=enemy, 3=selfANDenemy, 4=enemygroup,
		// 5=owngroup, 6=selfANDenemygroup, 7=owngroupANDenemy
		switch (ziel) {
		case 1:
			abilityTargetRest = AbilityTargetRestriction.SELF;
			break;
		case 2:
			abilityTargetRest = AbilityTargetRestriction.ENEMY;
			break;
		case 3:
			abilityTargetRest = AbilityTargetRestriction.SELFANDENEMY;
			break;
		case 4:
			abilityTargetRest = AbilityTargetRestriction.ENEMYGROUP;
			break;
		case 5:
			abilityTargetRest = AbilityTargetRestriction.OWNGROUP;
			break;
		case 6:
			abilityTargetRest = AbilityTargetRestriction.SELFANDENEMYGROUP;
			break;
		case 7:
			abilityTargetRest = AbilityTargetRestriction.OWNGROUPANDENEMY;
			break;
		default:
			abilityTargetRest = AbilityTargetRestriction.DEFAULT;
			break;
		}
		return abilityTargetRest;
	}

	/**
	 * Holt eine {@link ArrayList} aus {@link Buff}s von einer {@link Ability} anhand der faehigkeitID
	 * @param faehigkeitID Die ID der {@link Ability}, für die die {@link ArrayList} der {@link Buff}s geholt wird
	 * @return Eine {@link ArrayList} aus {@link Buff}s
	 */
	public ArrayList<fh.tagmon.gameengine.abilitys.Buff> getBuff(
			int faehigkeitID) {

		String sqlQuery = "SELECT tBuff.id, tBuff.dauer, tBuff.ziel, tBuff.bStaerke, "
				+ "		tBuff.bIntelligenz, tBuff.bKonstitution "
				+ "FROM tagdb_buff tBuff "
				+ "WHERE tBuff.faehigkeit_id = "
				+ faehigkeitID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		ArrayList<fh.tagmon.gameengine.abilitys.Buff> buffList = new ArrayList<fh.tagmon.gameengine.abilitys.Buff>();

		while (cursor.moveToNext()) { // int id, int duration,
										// AbilityTargetRestriction
										// abilityTargetRestriction, int
										// strengthBuff, int intelligenceBuff,
										// int constitutionBuff
			buffList.add(new fh.tagmon.gameengine.abilitys.Buff(cursor
					.getInt(0), cursor.getInt(1),
					getAbilityTargetRestriction(cursor.getInt(2)), cursor
							.getInt(3), cursor.getInt(4), cursor.getInt(5)));
		}
		return buffList;

	}

	/**
	 * Holt anhand der monsterID, die Stats des {@link Monster}s
	 * @param monsterID Die {@link Integer}-ID der Monsters
	 * @return Gibt das {@link Stats} Obejkt des {@link Monster}s zurück
	 * @throws MonsterDAOException
	 */
	public Stats getStats(int monsterID) throws MonsterDAOException {

		String sqlQuery = "SELECT tagdb_stats.maxHP,tagdb_stats.curHP,tagdb_stats.curHP,tagdb_stats.maxEP, "
				+ "		tagdb_stats.curEP,tagdb_stats.regEP, tagdb_stats.lvl, tagdb_stats.curEXP, tagdb_stats.defense "
				+ "FROM tagdb_stats "
				+ "WHERE tagdb_stats.monster_id = "
				+ monsterID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		if (!cursor.moveToFirst())
			throw new MonsterDAOException("Monster has no stats");

		return new Stats(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
				cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
				cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));

	}

	/**
	 * Holt anhand einer {@link Integer} MonsterID, das passende {@link Monster} aus der Datenbank
	 * 
	 * @param monsterID Die {@link Integer} ID des {@link Monster}
	 * @return Das {@link Monster} Objekt, welches anhand der monsterID aus der DB geholt worden ist
	 * @throws MonsterDAOException
	 */
	public Monster getMonsterByID(int monsterID) throws MonsterDAOException {

		Attribut attribut = getAttribute(monsterID);

		ArrayList<Koerperteil> koerperteile = null;
		try {
			koerperteile = getKoerperteile(monsterID);
		} catch (MonsterDAOException e) {
			Log.d("tagmonDB", "Koerperteil could not be created");
		}

		Stats stats = getStats(monsterID);

		String sqlQuery = "SELECT name, beschreibung " + "FROM tagdb_monster "
				+ "WHERE id = " + monsterID;
		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		if (!cursor.moveToFirst())
			throw new MonsterDAOException("Monster has no name");

		String monsterName = cursor.getString(0);
		String monsterBeschreibung = cursor.getString(1);

		Monster monster = new Monster(monsterID, monsterName,
				monsterBeschreibung, attribut, koerperteile, stats);
		return monster;
	}

	/**
	 * Holt anhand der {@link String} tagSerial, die zugehörige Monstergruppen
	 * ID
	 * 
	 * @param tagSerial
	 *            Der {@link String} der tagSerial
	 * @return
	 */
	public ArrayList<Integer> getMonsterGroupsByTagID(String tagSerial) {

		// getting monstergroup id's for specific tagSerial (1 tag can have
		// several groups)
		String sqlQuery = "SELECT monstergruppe_id "
				+ "FROM tagdb_tag_monsterGruppe tmg "
				+ "INNER JOIN tagdb_tag ta " + "	ON tmg.tag_id = ta.id "
				+ "WHERE ta.tagserial =  '" + tagSerial + "'";

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		ArrayList<Integer> groupIDList = new ArrayList<Integer>();

		while (cursor.moveToNext()) {
			groupIDList.add(cursor.getInt(0));
		}
		return groupIDList;
	}

	/**
	 * Holt eine {@link ArrayList} aus {@link Monster} von einer Monster Gruppe,
	 * aus der Datenbank
	 * 
	 * @param monsterGroupID
	 *            Die ID der Monstergruppe, aus der die {@link ArrayList} aus
	 *            {@link Monster}m geholt werden soll
	 * @return
	 */
	public ArrayList<Integer> getMonsterByGroupID(int monsterGroupID) {
		String sqlQuery = "SELECT monster_id "
				+ "FROM tagdb_monstergruppe_monster "
				+ "WHERE monstergruppe_id =  " + monsterGroupID;

		Cursor cursor = myDataBase.rawQuery(sqlQuery, null);

		ArrayList<Integer> monsterIDList = new ArrayList<Integer>();

		while (cursor.moveToNext()) {
			monsterIDList.add(cursor.getInt(0));
		}
		return monsterIDList;
	}

}
