package fh.tagmon.gameengine.helperobjects;

import java.util.ArrayList;
import java.util.List;

import fh.tagmon.gameengine.gameengine.PlayerInfo;

public class SummaryObject {
	private final StringBuilder summary = new StringBuilder();
	private final List<PlayerInfo> infos = new ArrayList<PlayerInfo>();
	
	public static SummaryObject getInstance() {
		return new SummaryObject();
	}

	private SummaryObject() {}
	
	public void append(String msg){
		summary.append(msg);
	}
	public String getSummary(){
		return summary.toString();
	}
	
	public void addPlayerInfo(PlayerInfo info){
		infos.add(info);
	}
	public List<PlayerInfo> getPlayerInfos(){
		return infos;
	}
	
	public void add(AnswerObject answer){
		summary.append(answer.getMsg());
		infos.add(answer.getPlayerInfo());
	}
}
