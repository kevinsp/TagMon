package fh.tagmon.network.message;

import java.util.HashMap;
import java.util.List;

import fh.tagmon.gameengine.gameengine.AbilityComponentList;
import fh.tagmon.gameengine.player.PlayerInfo;
import fh.tagmon.gameengine.helperobjects.ActionObject;
import fh.tagmon.gameengine.helperobjects.AnswerObject;
import fh.tagmon.gameengine.helperobjects.SummaryObject;

public class MessageFactory {
	//Host-Message-Builder
	
	public static MessageObject<AbilityComponentList> 
			createHostMessage_AbilityComponentList(AbilityComponentList acl){
		return new MessageObject<AbilityComponentList>(MessageType.ABILITY_COMPONENT, acl, -1);
	}
	
	public static MessageObject<String> 
			createHostMessage_GameOver(String msg){
		return new MessageObject<String>(MessageType.GAME_OVER, msg, -1);
	}
	
	public static MessageObject<SummaryObject> 
			createHostMessage_Summary(SummaryObject msg){
		return new MessageObject<SummaryObject>(MessageType.SUMMARY, msg, -1);
	}
	
	public static MessageObject<List<PlayerInfo>> 
			createHostMessage_YourTurn(List<PlayerInfo> playerList){
		return new MessageObject<List<PlayerInfo>>(MessageType.YOUR_TURN, playerList, -1);
	}
	
	public static MessageObject<Integer> 
			createHostMessage_GameStart(int playerID){
		return new MessageObject<Integer>(MessageType.GAME_START, playerID, -1);
	}
	
	//Client-Message-Builder
	public static MessageObject<ActionObject> 
			createClientMessage_Action(ActionObject act, final int playerID){
		return new MessageObject<ActionObject>(MessageType.ACTION, act, playerID);
	}
	public static MessageObject<AnswerObject> 
			createClientMessage_Answer(AnswerObject ans, final int playerID){
		return new MessageObject<AnswerObject>(MessageType.ANSWER, ans, playerID);
	}
	public static MessageObject<String> 
			createClientMessage_GameStart(String playerName, final int playerID){
		return new MessageObject<String>(MessageType.GAME_START, playerName, playerID);
	}
	
	
	/* TEMPLATE
	public static MessageObject<[CONTENT-TYPE]> getMessage_([CONTENT-TYPE] [CONTENT]){
		return new MessageObject<[CONTENT-TYPE]>(MessageType.[MSG-TYPE], [CONTENT]);
	}
	*/
}
