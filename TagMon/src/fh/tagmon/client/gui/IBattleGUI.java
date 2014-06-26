package fh.tagmon.client.gui;


import java.util.List;

import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.player.PlayerInfo;

public interface IBattleGUI {

    public boolean initBattleGUI(List<PlayerInfo> players, int userId, List<Ability> abilities);
    void refreshGUI(List<PlayerInfo> players, Enum<GuiPartsToUpdate> partToUpdate);
    void chooseAbility(ISetAbility setAbility);
}
