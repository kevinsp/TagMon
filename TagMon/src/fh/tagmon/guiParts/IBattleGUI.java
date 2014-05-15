package fh.tagmon.guiParts;


import java.util.LinkedList;

import fh.tagmon.gameengine.gameengine.PlayerListNode;

public interface IBattleGUI {

    public boolean initBattleGUI(LinkedList<PlayerListNode> players, int userId);
    public void refreshGUI(PlayerListNode node, String attr, int value);
}
