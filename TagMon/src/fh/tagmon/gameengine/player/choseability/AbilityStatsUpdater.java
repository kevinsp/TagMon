package fh.tagmon.gameengine.player.choseability;

import java.util.HashMap;
import java.util.Map.Entry;





import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.IListener;
import fh.tagmon.gameengine.player.IPlayer;
import fh.tagmon.model.Monster;

public class AbilityStatsUpdater {


	private void prepAbilityWithStats(Ability plainAbility, Monster monster){	
		plainAbility.setReqStats(monster);
	}

	protected void prepareAllAbilitys(Monster monster){
		for(Ability abil: monster.getAbilitys()){
			this.prepAbilityWithStats(abil,monster);
		}
	}

}
