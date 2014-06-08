package fh.tagmon.gameengine.gameengine;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import fh.tagmon.BuildConfig;
import fh.tagmon.client.GameClientEngine;
import fh.tagmon.database.daoImpl.MonsterDAOImpl;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.model.Monster;
import fh.tagmon.network.ConnectionType;
import fh.tagmon.rollestestecke.AsynkTaskDummy;
import fh.tagmon.rollestestecke.ClientConnector;
import fh.tagmon.rollestestecke.FakeSocket;
import fh.tagmon.rollestestecke.HostConnector;
import fh.tagmon.rollestestecke.MyMonsterCreator;
import fh.tagmon.rollestestecke.RollesTestKi;

public class GameEngineModule {

    private boolean fuerRolleZumTesten = true;
    
    private GameHostEngine hostEngine;
    private GameClientEngine clientEngine;
    
    

    


    public GameEngineModule(Activity context) {

//        PlayerList playerList = preparePlayerList();
//        initializeHost(playerList);
//        initializeClient(context);
    	
    	/*      Rolles Tests          */
    	
    	//RedKi
    	//Monster emulator
    	MyMonsterCreator myMonsterCreator = new MyMonsterCreator();
    	
    	//network Emulator
    	FakeSocket contBetweenHostAndRed = new FakeSocket();
    	//verbinde Host mit RedKi
    	HostConnector conToRed = new HostConnector(contBetweenHostAndRed);
    	//verbinde RedKi mit Host
    	ClientConnector conRedToHost = new ClientConnector(contBetweenHostAndRed);
    	//erstelle RedKi
    	Monster redMonster = myMonsterCreator.getMonsterDummy();
    	RollesTestKi redKi = new RollesTestKi("RedKi", redMonster, conRedToHost);
     
    	//BlueKi
    	
    	//network Emulator
    	FakeSocket contBetweenHostAndBlue = new FakeSocket();
    	//verbinde Host mit BlueKi
    	HostConnector conToBlue = new HostConnector(contBetweenHostAndBlue);
    	//verbinde BlueKi mit Host
    	ClientConnector conBlueToHost = new ClientConnector(contBetweenHostAndBlue);
    	//erstelle BlueKi
    	Monster blueMonster = myMonsterCreator.getMonsterDummy();
    	RollesTestKi blueKi = new RollesTestKi("BlueKi", blueMonster, conBlueToHost);
    	
    	//erstelle PlayList
		PlayerList playerList = new PlayerList();
		playerList.addPlayer(conToBlue);
		playerList.addPlayer(conToRed);
    	 
    	//erstelle hostengine
    	GameHostEngine gameHost = new GameHostEngine(playerList);

    	// bereite Spiel Start vor
    	AsynkTaskDummy host = new AsynkTaskDummy(gameHost, null);
    	AsynkTaskDummy red = new AsynkTaskDummy(null, redKi);
    	AsynkTaskDummy blue = new AsynkTaskDummy(null, blueKi);
    	
    	// starte Spiel
    	host.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null); // damit wirklich nebenläufig
    	red.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
    	blue.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
    	
    }


	private void initializeClient(Context context) {
		//TODO Zentraler Speicherplatz für MonsterPlayModule
		MonsterPlayModule module = null;
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
		if(hostEngine != null)
			hostEngine.go();
		//if(clientEngine != null) auskommentiert Rolle
			//clientEngine.run();	auskommentiert Rolle
	}
	
	
	
}
