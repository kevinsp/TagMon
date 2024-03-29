/**
 * Author: Christian Roletscheck & Christian Schmid
 */


package fh.tagmon.gameengine.player.deal_with_incoming_abilitys;

import android.util.Log;

import java.util.List;

import fh.tagmon.client.Helper_PlayerSettings;
import fh.tagmon.gameengine.abilitys.Buff;
import fh.tagmon.gameengine.abilitys.Damage;
import fh.tagmon.gameengine.abilitys.Heal;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.abilitys.Schadensabsorbation;
import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.player.IListener;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.model.Monster;

public class AbilityComponentDirector implements IListener{

	
	private Monster monster;
	private BuffHandler BuffHandler;
	private DamageHandler dmgHandler;
	private DamageAbsorbationHandler dmgAbsHandler;
	private HealHandler healHandler;
	private AbilityComponentLogger compLogger = new AbilityComponentLogger();
	
	
	public AbilityComponentDirector(Monster monster){
		this.monster = monster;
		this.BuffHandler = new BuffHandler(monster,compLogger);
		this.dmgHandler = new DamageHandler(monster,compLogger);
		this.dmgAbsHandler = new DamageAbsorbationHandler(monster, compLogger);
		this.healHandler = new HealHandler(monster, compLogger);
	}
	
	
	public AnswerObject handleAbilityComponents(AbilityComponentList abilityComponents, PlayerInfo info){
		boolean first = true;
		AnswerObject answer = new AnswerObject();
		for(IAbilityComponent ac : abilityComponents.getAbilityList()){
			answer.appendMsg(handleAbilityComponent(ac, first, info.NAME));
			first = false;
		}
		answer.setMonsterIsDead(Helper_PlayerSettings.monsterIsDead);
		info.setCurrentLife(monster.getCurrentLifePoints());
		info.setMaxLife(monster.getMaxLifePoints());
		answer.setPlayerInfo(info);
		return answer;
	}
	
	private String handleAbilityComponent(IAbilityComponent abilityComponent, boolean first, String name){
		String event = first ? "Das Monster von " + name + " hat " : "Auerdem hat es ";
		switch(abilityComponent.getComponentType()){
		case BUFF:
			Buff buff_debuff = (Buff) abilityComponent;
			this.BuffHandler.handleBuff(buff_debuff);
			event += "einen Buff auf sich gewirkt.";
			break;
		case DAMAGE:
			Damage dmgObj = (Damage) abilityComponent;
			int dmg = this.dmgHandler.handleDamage(dmgObj);
			event += "durch einen Angriff " + dmg + " schaden erlitten!";
			break;
		case HEAL:
			Heal healObj = (Heal) abilityComponent;
			int heal = this.healHandler.handleHeal(healObj);
			event += "durch einen Heal " + heal + " Lebenspunkte erhalten!";
			break;
		case SCHADENSABSORBATION:
			Schadensabsorbation schadenAbs = (Schadensabsorbation) abilityComponent;
			
			//testiVonRolle(schadenAbs); // ob CloneMe weggelassen werden kann (Rolle)
			
			this.dmgAbsHandler.handleDamageAbsorbation(schadenAbs);
			event += "sicht mit einem natuerlichen Schild gewappnet!";
			break;
		case STUN:
			event += "die Kontrolle ber seinen Koerper verloren!";
			break;
		default:
			break;
		
		}
		Log.i("AbilityComponentDirector", "[AbilityComponentDirector] logged :: " + getLatestLog());
		return event;
	}
	
	public String getLatestLog(){
		return this.compLogger.getLatestLogMsg();
	}

	@Override
	public void newRound(List<PlayerInfo> targetList, int yourTargetId) {

		this.compLogger.newRound();
		this.BuffHandler.newRound();
		
	}


	private void testiVonRolle(Schadensabsorbation schadenAbs){
		///TEST
		Log.i("GameEngine", "KI resv ... abs:" + String.valueOf(schadenAbs.getAbsorbationAmount()));
		///
	}
}
