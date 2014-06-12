package fh.tagmon.client.gui;


import java.util.LinkedList;

import fh.tagmon.gameengine.gameengine.PlayerListNode;

public interface IBattleGUI {

    public boolean initBattleGUI(LinkedList<PlayerListNode> players, int userId);
    void refreshGUI(int id, Enum<GuiPartsToUpdate> partToUpdate, Object value);
    void chooseAbility(ISetAbility setAbility);
}
