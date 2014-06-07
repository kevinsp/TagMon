package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.MonsterPlayModule;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;
import fh.tagmon.model.Monster;

public class RollesTestKi {

    private String kiName;
    private MonsterPlayModule playModule;
    private int id;
    
    public RollesTestKi(String name, Monster myMonster, int id) {
        this.kiName = name;

        this.id = id;
        this.playModule = new MonsterPlayModule(myMonster);
    }   
    
    private int myRandomWithHigh(int low, int high) {
        // Zufallszahlen inklusive Obergrenze
        high++;
        return (int) (Math.random() * (high - low) + low);
    }
    
    
    private Ability choseRandomAbility() {
        int maxAbil = playModule.getAbilityChooser().getAllAbilitys().keySet().size() - 1; //da von 0 an gezählt
    	int random = this.myRandomWithHigh(0, maxAbil);
        
    	return this.playModule.getAbilityChooser().getAbility(random);
    }

    private AbilityTargetRestriction choseRandomTarget(Ability chosenAbility) {
        return chosenAbility.getTargetRestriction();
    }
 
    
    
}
