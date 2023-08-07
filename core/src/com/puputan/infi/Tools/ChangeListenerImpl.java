package com.puputan.infi.Tools;

import Screens.GameScreen;
import Screens.GameStatesEnum;
import Screens.Stages.UiDataObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ChangeListenerImpl extends ChangeListener {


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        if(actor.getUserObject() instanceof UiDataObject) {
            UiDataObject uiDataObject = (UiDataObject) actor.getUserObject();
            GameScreen.playerObject.getPlayerSystems().activatePowerUp(uiDataObject.getPowerUpsEnum());
            GameScreen.gameStates = GameStatesEnum.Running;
            GameScreen.powerUpChooseUIStage.clearButtonsList();
        }
    }
}
