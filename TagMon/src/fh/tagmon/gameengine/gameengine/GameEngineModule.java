package fh.tagmon.gameengine.gameengine;


import android.app.Activity;
import fh.tagmon.BuildConfig;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.gameengine.player.MyPlayerCreator;
import fh.tagmon.gameengine.testDmgAbsorbationHandler.RunTestDmgAbsorbationHandler;

public class GameEngineModule {

    private boolean fuerRolleZumTesten = true;


    public GameEngineModule(Activity context) {

        if (BuildConfig.DEBUG) {
            // der erste part ist zum testen für rolle ;)
            // das else ist für pascal zum testen mit der gui
            if (fuerRolleZumTesten) {
                IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster", 0, true);
                IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster", 1, true);
                PlayerList playerList = new PlayerList();
                playerList.addPlayer(redKi);
                playerList.addPlayer(blueKi);
                GameHostEngine playEngine = new GameHostEngine(playerList, context);
                playEngine.run();
            	
//            	RunTestDmgAbsorbationHandler testDmgAb = new RunTestDmgAbsorbationHandler();
//            	testDmgAb.run();
            	
            } else {
                IPlayer redKi = MyPlayerCreator.getPlayer("Red", "RedMonster", 0, false);
                IPlayer blueKi = MyPlayerCreator.getPlayer("Blue", "BlueMonster", 1, true);
                PlayerList playerList = new PlayerList();
                playerList.addPlayer(redKi);
                playerList.addPlayer(blueKi);
                GameHostEngine playEngine = new GameHostEngine(playerList, context);
                playEngine.execute();
            }

//            playEngine.run();

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
