package fh.tagmon.network.message;

public enum MessageType {
	//HostMessageType
	ABILITY_COMPONENT(true), 
	GAME_OVER(true), 
	GAME_START(true),
	SUMMARY(true),
	YOUR_TURN(true),
	WELCOME(true),
	
	//ClientMessageType
	ACTION(false),
	ANSWER(false),
	;
	
	public final static boolean IS_HOST_MSG_TYPE = true;
	public final boolean IS_CLIENT_MSG_TYPE = false;
	private final boolean type;
	
	MessageType(boolean isHost){
		type = isHost;
	}
	
	public boolean isHostMessageType(){
		return type == IS_HOST_MSG_TYPE;
	}
	public boolean isClientMessageType(){
		return type == IS_CLIENT_MSG_TYPE;
	}
}
