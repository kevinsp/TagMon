package fh.tagmon.gameengine.abilitys;

public interface IDurationAbilityComponent {

	public int getDuration();
	
	//wird nur gebraucht sofern objecte nicht über netzwerk geschickt werden
	public IDurationAbilityComponent cloneMe();
}
