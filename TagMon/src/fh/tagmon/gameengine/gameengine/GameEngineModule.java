package fh.tagmon.gameengine.gameengine;


import android.app.Activity;

import fh.tagmon.BuildConfig;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.MyPlayerCreator;

public class GameEngineModule {

    public GameEngineModule(Activity context) {

        if (BuildConfig.DEBUG) {
            IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster",0);

            IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster", 1);
            PlayerList playerList = new PlayerList();
            playerList.addPlayer(redKi);
            playerList.addPlayer(blueKi);
            GamePlayEngine playEngine = new GamePlayEngine(playerList, context);

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
