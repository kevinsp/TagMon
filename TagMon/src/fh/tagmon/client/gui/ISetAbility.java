package fh.tagmon.client.gui;

import fh.tagmon.gameengine.helperobjects.ActionObject;

/**
 * Created by pasca_000 on 07.06.2014.
 */
public interface ISetAbility {
    public void setAbility(ActionObject actionObject);

    void onResumeDialog();
    void closeGame();
}
