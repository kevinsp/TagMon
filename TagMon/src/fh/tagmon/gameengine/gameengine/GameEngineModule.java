package fh.tagmon.gameengine.gameengine;


import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import fh.tagmon.client.clientEngine.GameClientEngine;
import fh.tagmon.database.dao.MonsterDAO;
import fh.tagmon.database.daoImpl.MonsterDAOImpl;
import fh.tagmon.exception.MonsterDAOException;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.model.Monster;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.rollestestecke.AsynkTaskHostDummy;
import fh.tagmon.rollestestecke.AsynkTaskKiDummy;
import fh.tagmon.rollestestecke.MyMonsterCreator;

public class GameEngineModule {

    private boolean fuerRolleZumTesten = true;
    
    private GameHostEngine hostEngine;
    private GameClientEngine clientEngine;

    private Monster ownMonster;
    private Context context;
    
 
    public GameEngineModule(Activity context, Monster monster) {
        this.context = context;
    }
 
    public void startGamePlayerVSTag(String tagSerNr) {

<<<<<<< HEAD
    	
=======

>>>>>>> branch 'master' of https://github.com/kevinsp/TagMon.git
    	//Starte den Host
    	int gamePlayerSize = 2; // Player vs Tag
    	startHostAsynkTask(gamePlayerSize);
    	
    	//dem Spieler sein Monster Holen
    	MyMonsterCreator mCreator = new MyMonsterCreator(); // eig aus der Db holen
    	Monster monsterFromPlayer = mCreator.getMonsterDummy();
    	
    	//Spieler bekommt sein Monster und verbindet sich mit dem Server
    	initializeClient(this.context, monsterFromPlayer);
    	this.clientEngine.execute();
    	
    	//der Ki ihr Monster holen
    	Monster kiMonster = mCreator.getMonsterDummy(); // eig aus der Db holen mit tagSerNr
    	
    	//Die Ki bekommt ihr Monster und verbindet sich mit dem Server
    	startKiAsynkTask("RED", kiMonster);
<<<<<<< HEAD
		
=======
>>>>>>> branch 'master' of https://github.com/kevinsp/TagMon.git


    	//this.testAbsorber();
    	//testKiVSKi();
    }

    

    
    private void startHostAsynkTask(int gamePlayerSize) {
		/*
		 * Startet einen Task der folgedes macht.
		 * 1. Wartet bis alle SpielTeilnehmer connectet sind.
		 * 2. Erstellt daraus eine Spieler liste und inizalisiert die HostGameEngine
		 * 3. Startet die Engine und das spiel luft ab
		 */
        AsynkTaskHostDummy host = new AsynkTaskHostDummy(gamePlayerSize);
        host.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null); // damit wirklich nebenlufig
	}

    private void initializeClient(Context context, Monster monster) {
		//TODO Zentraler Speicherplatz fr MonsterPlayModule
		MonsterPlayModule module = new MonsterPlayModule(monster);
		ConnectionType connectionType = ConnectionType.LCL_SOCKET;

        clientEngine = new GameClientEngine(context, module, connectionType);

	}


	
	private Monster getMonsterFromTobi(Context context){
		try {
			MonsterDAO monsterDAO = new MonsterDAOImpl(context);
			Monster monster = monsterDAO.getMonster("tag1");
			
			return monster;
		} catch (SQLException e1) {
			Log.d("tagmonDB", "SQLEX");
		} catch (IOException e1) {
			Log.d("tagmonDB", "IOEX");
		} catch (MonsterDAOException e){
	    	Log.d("tagmonDB", "MonsterDAO");
			e.printStackTrace();
		} catch (Exception e) {
			Log.d("tagmonDB", "FAIIIIIIIIIIIIIIL");
			e.printStackTrace();
		}
	
	  return null;
	}
	
	private void startKiAsynkTask(String kiName, Monster kiMonster){
		/*
		 * Startet einen Task der folgedes macht.
		 * 1. Verbindet sich mit dem Server ber ein Local-Socket. ACHTUNG SERVER MUSS SCHON LAUFEN!!
		 * 2. Spielt das Spiel
		 */
		AsynkTaskKiDummy newKi = new AsynkTaskKiDummy(kiMonster, kiName);
		newKi.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
	}
	
	private void testKiVSKi(){
		startHostAsynkTask(2);
		
		MyMonsterCreator mCreator = new MyMonsterCreator();
		Monster redM = mCreator.getMonsterDummy();
		Monster blueM = mCreator.getMonsterDummy();
		
		startKiAsynkTask("RED", redM);
		startKiAsynkTask("BLUE", blueM);	
	}
	
	private void testAbsorber(){
		startHostAsynkTask(2);
		
		MyMonsterCreator mCreator = new MyMonsterCreator();
		Monster redM = mCreator.getAttackMonster();
		Monster blueM = mCreator.getAbsorberMonster();
		
		startKiAsynkTask("RED", redM);
		startKiAsynkTask("BLUE", blueM);
	}
}
