package fh.tagmon.rollestestecke;

import fh.tagmon.gameengine.helperobjects.AnswerObject;

public class RollesClientAnswerMessage extends RollesClientNetworkMessage implements RollesIClientNetworkMessage{

	private final AnswerObject answer;
	
	public RollesClientAnswerMessage(AnswerObject answer){
		super(RollesClientNetworkMessageTypes.ANSWER);
		this.answer = answer;

	}
	

	
	@Override
	public RollesClientNetworkMessageTypes getMessageType() {
		return super.messageType;
	}



	public AnswerObject getAnswer() {
		return answer;
	}
}
