package fh.tagmon.gameengine.gameengine;


import fh.tagmon.BuildConfig;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.MyPlayerCreator;
import fh.tagmon.gameengine.gameengine.PlayerList;

public class GameEngineModule {

	public GameEngineModule(){
		if (BuildConfig.DEBUG){
        	IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster");
        	IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster");
			PlayerList playerList = new PlayerList();
			playerList.addPlayer(redKi);
			playerList.addPlayer(blueKi);
			GamePlayEngine playEngine = new GamePlayEngine(playerList);
			
			playEngine.run();
        	
//        	RunBuffHandlerTest buffHandler = new RunBuffHandlerTest();
//        	buffHandler.run();
			
//			RunPrepareAbilityTest prepareHandler = new RunPrepareAbilityTest();
//			prepareHandler.run();
			
//			RunPrepareTargetTest prepTest = new RunPrepareTargetTest();
//			prepTest.run();
//			
//			RunAbilityComponentLogger logger = new RunAbilityComponentLogger();
//			logger.run();
			
        }

	}
}
