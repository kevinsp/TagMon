package fh.tagmon.database.daoImpl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
 * Diese {@link DataBaseHelperLocal} Klasse wird daf�r verwendet, um das Monster des Spielers in der lokalen Datenbank 
 * abzuspeichern.
 * Da sich dieses Monster im laufe des Spiels ver�nder kann, werden update Methoden gebraucht um diese �nderungen auch
 * in der Datenbank abspeichern zu k�nnen.
 * In dieser Datenbank wird auch der Player abgespeichert.
 * Beim erstmaligen starten der App, wird eine leere lokale Datenbankstruktur anhand einens Skripts erstellt
 * und anschlie�end mit einem Dummy Monster, ebenfalls anhand eines Skripts, bef�llt.
 *
 */
public class DataBaseHelperLocal extends SQLiteOpenHelper {

	/** Der Standard Pfad zur Anwendunsgdatenbank */
	private static String DB_PATH = "/data/data/fh.tagmon/databases/";

	/** Der Name der lokalen Datenbank */
	private static String DB_NAME = "localDB.sqlite3";

	/** Der Name des SQL Build Scripts um die locale Datenbank aufzubauen */
	private static String BUILD_SCRIPT = "dbLocalEmptyBuildScript.sql";

	/** Der Name des SQL Build Scripts um das Anfangs Monster zu generieren*/
	private static String DUMMYMONSTER_SCRIPT = "dummyMonsterScript.sql";

	/** Die {@link SQLiteDatabase} */
	private SQLiteDatabase myDataBase;

	/** Der App {@link Context}*/
	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelperLocal(Context context) {

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
			this.getWritableDatabase();

			try {
				// copyDataBase();
				Log.d("tagmonDB", "db structure create");
				createInitialLokaleDatabase();
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
							| SQLiteDatabase.OPEN_READWRITE);
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
				SQLiteDatabase.NO_LOCALIZED_COLLATORS
						| SQLiteDatabase.OPEN_READWRITE);
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
	 * Holt die {@link Attribut} nach einer bestimmten MonsterID aus der
	 * Datenbank
	 * 
	 * @param monsterID
	 *            Die ID des Monsters
	 * @return Das {@Attribut} Objekt
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
	 * Holt alle {@link Koerperteil} eines {@link Monster}s aus der Datenbank
	 * 
	 * @param monsterID
	 *            Die ID des {@link Monster}
	 * @return Eine {@link ArrayList} mit den {@link Koerperteil} Objekten
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
	 * Holt die {@link AttributModifikator}en eine {@link Koerperteil}s aus der
	 * Datenbank
	 * 
	 * @param koerperteileID
	 *            Die ID des {@link Koerperteil}
	 * @return {@link AttributModifikator}
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
	 * Holt eine Liste an {@link Ability}s von einem {@link Koerperteil} aus der
	 * Datenbank
	 * 
	 * @param koerperteileID
	 *            Der {@link Integer} Wert des {@link Koerperteil}, f�r das die
	 *            {@link Ability}List geholt werden soll
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
	 * Holt eine {@link ArrayList} aus {@link Buff}s von anhand einer
	 * {@link Integer} faeigkeitID aus der Datenbank
	 * 
	 * @param faehigkeitID
	 *            Der {@link Integer} Wert der Faehigkeit, f�r die die
	 *            {@link Buff} Liste geholt wird
	 * @return {@link ArrayList} aus {@link Buff}s
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
	 * Holt die {@link Stats} von einem {@link Monster} aus der Datenbank
	 * 
	 * @param monsterID
	 *            Die ID des {@link Monster}
	 * @return Das {@link Stats} Objekt
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
	 * Holt ein {@link Monster} nach einer bestimmten {@link Integer} Wert ID
	 * aus der Datenbank
	 * 
	 * @param monsterID
	 *            Die ID des Monsters
	 * @return Das {@link Monster} Objekt
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
	 * Aktualisiert das {@link Monster} in der Datenbank
	 * 
	 * @param monster
	 *            Das {@link Monster}, welches in der DB aktualisiert werden
	 *            soll
	 * @throws MonsterDAOException
	 */
	public void updateMonster(Monster monster) throws MonsterDAOException {
		updateAttribute(monster);

		for (Koerperteil koerperteil : monster.getKoerperteileList()) {
			updateKoerperteil(koerperteil);
			updateFaehigkeit(koerperteil);
		}
		updateStats(monster);
	}

	/**
	 * Erstellt einen Spieler in der Datenbank mit einem Name {@link String}
	 * 
	 * @param name
	 *            Der {@link String} Wert f�r den Namen
	 */
	public void createPlayer(String name) {
		String SQLStatement = "INSERT INTO tagdb_player (name) VALUES(\""
				+ name + "\")";
		myDataBase.execSQL(SQLStatement);
	}

	/**
	 * Erstellt aus einem {@link Int} Wert die {@link AbilityTargetRestriction}
	 * 
	 * @param ziel
	 *            Der {@link Integer} Wert aus dem die
	 *            {@link AbilityTargetRestriction} erstellt werden soll
	 * @return
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
	 * Erstellt die Struktur der lokalen Datenbank und erstellt ein Dummy
	 * Monster
	 * 
	 * @throws IOException
	 */
	private void createInitialLokaleDatabase() throws IOException {
		createDbStructureFromFile();
		insertDummyMonster();
	}

	/**
	 * Aktualisiert das {@link Attribut} Objekt des {@link Monster}s
	 * 
	 * @param monster
	 *            Das {@link Monster}, f�r das die {@link Attribut}e in der DB
	 *            aktualisiert werden sollen
	 * @throws MonsterDAOException
	 */
	private void updateAttribute(Monster monster) throws MonsterDAOException {
		Attribut attr = monster.getAttributes();

		String sqlQuery = " UPDATE tagdb_attribute " + " SET staerke = "
				+ attr.getStaerke() + ", intelligenz= " + attr.getIntelligenz()
				+ ", konstitution= " + attr.getKonstitution()
				+ " WHERE tagdb_attribute.monster_id = " + monster.id;

		myDataBase.execSQL(sqlQuery);
	}

	/**
	 * Erstellt die Struktur f�r die lokale Datenbank aus dem
	 * dbLocalEmptyBuildScript Der Pfad steht in BUILD_SCRIPT
	 * 
	 * @throws IOException
	 */
	private void createDbStructureFromFile() throws IOException {

		openDataBase();
		List<String> stmtList = getStatementList(BUILD_SCRIPT);
		for (String stmt : stmtList) {
			myDataBase.execSQL(stmt);
		}
	}

	/**
	 * F�gt ein Dummy Monster aus einem SQL Script (DUMMYMONSTER_SCRIPT) in die
	 * Datenbank ein
	 * 
	 * @throws IOException
	 */
	private void insertDummyMonster() throws IOException {
		openDataBase();

		List<String> stmtList = getStatementList(DUMMYMONSTER_SCRIPT);
		Log.d("tagmonDB", DUMMYMONSTER_SCRIPT);
		for (String stmt : stmtList) {
			Log.d("tagmonDB", stmt);
			myDataBase.execSQL(stmt);
		}
	}

	/**
	 * Erstellt aus einem SQL Script mit mehreren Statements eine Liste mit
	 * einzelnen Statements
	 * 
	 * @param sqlFilePath
	 *            Der Pfad zu dem SQL Script
	 * @return Die Liste mit den einzelnen SQL Scripts
	 * @throws IOException
	 */
	private List<String> getStatementList(String sqlFilePath)
			throws IOException {
		InputStream insertsStream = myContext.getAssets().open(sqlFilePath);
		BufferedReader insertReader = new BufferedReader(new InputStreamReader(
				insertsStream));

		LinkedList<String> stmtList = new LinkedList<String>();
		String statement = "";

		while (insertReader.ready()) {
			statement += insertReader.readLine();
			if (statement.contains(";")) {
				stmtList.add(statement);
				statement = "";
			}
		}
		insertReader.close();

		return stmtList;
	}

	/**
	 * Aktualisiert {@link Koerperteil}
	 * 
	 * @param Das
	 *            {@link Koerperteil} das in der Datenbank aktualisiert werden
	 *            soll
	 */
	private void updateKoerperteil(Koerperteil koerperteil) {

		int kArt = 0;
		// 1=Kopf, 2=Torso, 3=Arm, 4=Bein
		switch (koerperteil.koerperteilArt) {
		case KOPF:
			kArt = 1;
			break;
		case TORSO:
			kArt = 2;
			break;
		case ARM:
			kArt = 3;
			break;
		case BEIN:
			kArt = 4;
		}

		// Update Koerperteil
		String sqlQuery = " UPDATE tagdb_koerperteile " + " SET name = '"
				+ koerperteil.name + "', art= " + kArt + " WHERE id = "
				+ koerperteil.id;
		myDataBase.execSQL(sqlQuery);

		// Update AttributModifikator from Koerperteil
		sqlQuery = " UPDATE tagdb_attributmodifikator " + " SET staerke = "
				+ koerperteil.getAttributModifikator().getStaerke()
				+ ", intelligenz= "
				+ koerperteil.getAttributModifikator().getIntelligenz()
				+ ", konstitution= "
				+ koerperteil.getAttributModifikator().getKonstitution()
				+ " WHERE koerperteile_id = " + koerperteil.id;
		myDataBase.execSQL(sqlQuery);

	}

	/**
	 * Aktualisiert die Abilitys f�r das �bergebene {@link Koerperteil}
	 * 
	 * @param koerperteil
	 *            Das {@link Koerperteil} welches in der DB aktualisiert werden
	 *            soll
	 */
	private void updateFaehigkeit(Koerperteil koerperteil) {

		String sqlQuery = "";

		// Faehigkeit attribute
		int abilityID = 0;
		String name = "";
		int ziel = 0;
		int energiekosten = 0;
		int angriffsziel = 0;
		int angriffswert = 0;
		int heilungsziel = 0;
		int heilungswert = 0;
		int stunziel = 0;
		int stundauer = 0;
		int absorbationziel = 0;
		int absorbationsdauer = 0;
		int schadensabsorbation = 0;

		// buff attribute
		int bDauer = 0;
		int bZiel = 0;
		int bStaerke = 0;
		int bIntelligenz = 0;
		int bKonstitution = 0;

		// iterate through all abilities of a 'koerperteil'..
		for (Ability ability : koerperteil.getAbilityList()) {
			abilityID = ability.getID();
			name = ability.getAbilityName();
			ziel = getZielFromTargetRestriction(ability.getTargetRestriction());
			energiekosten = ability.getEnergyCosts();

			// get different components for example 'faustschlag' has a dmg
			// component and stun component -> get the 2 components
			// and change the angriffswert/angriffsziel and stun attributes, the
			// ability has e.g. no heal component -> heilungswert = 0 and so on
			for (IAbilityComponent abilityComponent : ability
					.getAbilityComponents()) {
				switch (abilityComponent.getComponentType()) {
				case BUFF:
					Buff buffObj = (Buff) abilityComponent;
					bDauer = buffObj.getDuration();
					bZiel = getZielFromTargetRestriction(buffObj
							.getComponentTargetRestriction());
					bStaerke = buffObj.getStrengthBuff();
					bIntelligenz = buffObj.getIntelligenceBuff();
					bKonstitution = buffObj.getConstitutionBuff();
					break;
				case DAMAGE:
					Damage dmgObj = (Damage) abilityComponent;
					angriffsziel = getZielFromTargetRestriction(dmgObj
							.getComponentTargetRestriction());
					angriffswert = dmgObj.getDamage();
					break;
				case HEAL:
					Heal healObj = (Heal) abilityComponent;
					heilungsziel = getZielFromTargetRestriction(healObj
							.getComponentTargetRestriction());
					heilungswert = healObj.getHealAmount();
					break;
				case SCHADENSABSORBATION:
					Schadensabsorbation absObj = (Schadensabsorbation) abilityComponent;
					absorbationziel = getZielFromTargetRestriction(absObj
							.getComponentTargetRestriction());
					absorbationsdauer = absObj.getDuration();
					schadensabsorbation = absObj.getAbsorbationAmount();
					break;
				case STUN:
					Stun stunObj = (Stun) abilityComponent;
					stunziel = getZielFromTargetRestriction(stunObj
							.getComponentTargetRestriction());
					stundauer = stunObj.getStunDuration();
					break;
				}
			}
			// After finishing iterate through all abilitycomponents of one
			// ability it updates the ability and the ability buff
			// UPDATE Ability
			sqlQuery = " UPDATE tagdb_faehigkeit " + " SET name = '" + name
					+ "', ziel=" + ziel + ", energiekosten=" + energiekosten
					+ ", angriffsziel=" + angriffsziel + ", angriffswert="
					+ angriffswert + ", " + " heilungsziel=" + heilungsziel
					+ ", heilungswert=" + heilungswert + ", stunziel="
					+ stunziel + ",stundauer=" + stundauer
					+ ", absorbationsziel=" + absorbationziel + ", "
					+ " absorbationsdauer=" + absorbationsdauer
					+ ", schadensabsorbation=" + schadensabsorbation
					+ " WHERE id = " + abilityID;
			myDataBase.execSQL(sqlQuery);

			// Update AbilityBUFF
			sqlQuery = " UPDATE tagdb_buff " + " SET dauer=" + bDauer
					+ ", ziel=" + bZiel + ", bStaerke=" + bStaerke
					+ ", bIntelligenz=" + bIntelligenz + ", bKonstitution="
					+ bKonstitution + " WHERE faehigkeit_id = " + abilityID;
			myDataBase.execSQL(sqlQuery);
		}
	}

	/**
	 * Aktualisiert das {@link Stats} Objekt f�r das �bergebene {@link Monster}
	 * 
	 * @param monster
	 *            Das {@link Monster} Obejkt, wo die {@link Stats} aktualisiert
	 *            in der DB werden soll
	 */
	private void updateStats(Monster monster) {
		Stats stats = monster.getStats();
		String sqlQuery = "UPDATE tagdb_stats " + "SET maxHP="
				+ stats.getMaxHP() + ", curHP=" + stats.getCurHP() + ", maxEP="
				+ stats.getMaxEP() + ", curEP=" + stats.getCurEP() + ", regEP="
				+ stats.getRegEP() + ", lvl=" + stats.getLvl() + ", curEXP="
				+ stats.getCurEXP() + ", defense=" + stats.getDefensye()
				+ " WHERE monster_id = " + monster.id;
		Log.d("tagmonDB", sqlQuery);
		myDataBase.execSQL(sqlQuery);
	}

	/**
	 * Holt aus {@link abilityTargetRestriction} den {@link int} Wert f�r das
	 * Angriffsziel
	 * 
	 * @param abilityTargetRestriction
	 * @return Der {@link int} Wert f�r das Ziel
	 */
	private int getZielFromTargetRestriction(
			AbilityTargetRestriction abilityTargetRestriction) {
		int ziel = 0;
		switch (abilityTargetRestriction) {
		case SELF:
			ziel = 1;
			break;
		case ENEMY:
			ziel = 2;
			break;
		case SELFANDENEMY:
			ziel = 3;
			break;
		case ENEMYGROUP:
			ziel = 4;
			break;
		case OWNGROUP:
			ziel = 5;
			break;
		case SELFANDENEMYGROUP:
			ziel = 6;
			break;
		case OWNGROUPANDENEMY:
			ziel = 7;
			break;
		}
		return ziel;
	}

}
