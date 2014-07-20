package fh.tagmon.client.gui;

import fh.tagmon.gameengine.helperobjects.ActionObject;

public interface ISetAbility {
    public void setAbility(ActionObject actionObject);

    void onResumeDialog();
    void closeGame();
}
