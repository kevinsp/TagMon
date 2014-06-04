package fh.tagmon.gameengine.gameengine;


import android.app.Activity;
import android.content.Context;
import fh.tagmon.BuildConfig;
import fh.tagmon.client.GameClientEngine;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.MyPlayerCreator;
import fh.tagmon.network.ConnectionType;

public class GameEngineModule {

    private boolean fuerRolleZumTesten = true;
    
    private GameHostEngine hostEngine;
    private GameClientEngine clientEngine;


    public GameEngineModule(Activity context) {

        if (BuildConfig.DEBUG) {
            // der erste part ist zum testen für rolle ;)
            // das else ist für pascal zum testen mit der gui
            if (fuerRolleZumTesten) {
                
                
            	
//            	RunTestDmgAbsorbationHandler testDmgAb = new RunTestDmgAbsorbationHandler();
//            	testDmgAb.run();
            	
            }
            
        }
        PlayerList playerList = preparePlayerList();
        initializeHost(playerList);
        initializeClient(context);
    }


	private void initializeClient(Context context) {
		//TODO Zentraler Speicherplatz f�r MonsterPlayModule
		MonsterPlayModule module = null;
		ConnectionType connectionType = ConnectionType.LCL_SOCKET;
		clientEngine = new GameClientEngine(context, module, connectionType);
	}


	private PlayerList preparePlayerList(){
		
		
		
		IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster", 0, true);
        IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster", 1, true);
        PlayerList playerList = new PlayerList();
        playerList.addPlayer(redKi);
        playerList.addPlayer(blueKi);
        return playerList;
	}
	
	private void initializeHost(PlayerList playerList) {
		hostEngine = new GameHostEngine(playerList);
	}
	
	private void startEngines(){
		if(hostEngine != null)
			hostEngine.run();
		if(clientEngine != null)
			clientEngine.run();
	}
}
