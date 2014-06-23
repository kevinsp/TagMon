package fh.tagmon.gameengine.gameengine;


import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;
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
    private Monster enemy;
    private Monster ownMonster;
    private Context context;
    
 
 public GameEngineModule(Activity context, Monster monster) {
        this.context = context;
        enemy = monster;

    }
    public void dosomething() {

        //  PlayerList playerList = preparePlayerList();
        initializeHost();

        MyMonsterCreator mCreator = new MyMonsterCreator();
        ownMonster = mCreator.getMonsterDummy();
        initializeClient(context, ownMonster);
        startEngines();
        //RollesTestKi rtk = new RollesTestKi("sponge", enemy);

        AsynkTaskKiDummy kiRed = new AsynkTaskKiDummy(enemy, "RED");

        kiRed.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);


//    	this.testWithLocalSocket();
    }

private void initializeHost() {
        AsynkTaskHostDummy host = new AsynkTaskHostDummy();
        host.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null); // damit wirklich nebenlufig

/*
        NetworkServerSocketConnection server = null;
        try {
            server = new NetworkServerSocketConnection(2);
            PlayerList playerList = server.getPlayers();
            hostEngine = new GameHostEngine(playerList);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
	}

    private void initializeClient(Context context, Monster monster) {
		//TODO Zentraler Speicherplatz fr MonsterPlayModule
		MonsterPlayModule module = new MonsterPlayModule(monster);
		ConnectionType connectionType = ConnectionType.LCL_SOCKET;

        clientEngine = new GameClientEngine(context, module, connectionType);

	}


	private PlayerList preparePlayerList(){
		
		
//		
//		IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster", 0, true);
//        IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster", 1, true);
        PlayerList playerList = new PlayerList();
//        playerList.addPlayer(redKi);
//        playerList.addPlayer(blueKi);
        return playerList;
	}
	
	private void initializeHost(PlayerList playerList) {
		hostEngine = new GameHostEngine(playerList);
	}
	
	private void startEngines(){
	/*	if(hostEngine != null)
			hostEngine.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);*/
		if(clientEngine != null)
			clientEngine.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);

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
	
	
	
	private void testWithLocalSocket(){
		MyMonsterCreator mCreator = new MyMonsterCreator();
		Monster redM = mCreator.getMonsterDummy();
		Monster blueM = mCreator.getMonsterDummy();
		
		AsynkTaskKiDummy kiRed = new AsynkTaskKiDummy(redM, "RED");
		AsynkTaskKiDummy kiBlue = new AsynkTaskKiDummy(blueM, "BLUE");
		
		AsynkTaskHostDummy host = new AsynkTaskHostDummy();
		
		host.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null); // damit wirklich nebenläufig
		kiRed.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		kiBlue.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		
		
	}
}
