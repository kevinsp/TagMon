package fh.tagmon.client.gui;


import java.util.LinkedList;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.gameengine.PlayerInfo;

public interface IBattleGUI {

    public boolean initBattleGUI(LinkedList<PlayerInfo> players, int userId, LinkedList<Ability> abilities);
    void refreshGUI(LinkedList<PlayerInfo> players, Enum<GuiPartsToUpdate> partToUpdate);
    void chooseAbility(ISetAbility setAbility);
}
