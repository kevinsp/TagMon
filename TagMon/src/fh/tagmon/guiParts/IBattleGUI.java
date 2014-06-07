package fh.tagmon.guiParts;


import java.util.HashMap;
import java.util.LinkedList;

import fh.tagmon.gameengine.gameengine.PlayerListNode;
import fh.tagmon.gameengine.player.IPlayer;

public interface IBattleGUI {

    public boolean initBattleGUI(LinkedList<PlayerListNode> players, int userId);
    public void refreshGUI(IPlayer player, Enum <GuiPartsToUpdate> partToUpdate);
    public void chooseAbility(HashMap<Integer, IPlayer> targetList, int yourTargetId, ISetAbility iSetAbility);
}
