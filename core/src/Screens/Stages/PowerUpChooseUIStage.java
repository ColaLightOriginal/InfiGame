package Screens.Stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.puputan.infi.Objects.Player.PowerUpsEnum;
import com.puputan.infi.Tools.ChangeListenerImpl;

import java.util.LinkedList;

public class PowerUpChooseUIStage extends Stage {

    private final Table table;
    private final TextButtonStyle textButtonStyle;
    private final ChangeListenerImpl changeListener;
    private LinkedList<TextButton> powerUpsButtons;

    public PowerUpChooseUIStage(Viewport viewport){
        super(viewport);
        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);
        this.addActor(table);

        changeListener = new ChangeListenerImpl();
        this.addListener(changeListener);
        textButtonStyle = buttonTextStyle();
        powerUpsButtons = new LinkedList<>();
    }

    public TextButtonStyle buttonTextStyle(){
        TextButtonStyle style = new TextButtonStyle();
        style.font = new BitmapFont();
        style.font.setColor(Color.WHITE);
        return style;
    }

    public void addButtons(LinkedList<PowerUpsEnum> powerUpsEnums){
        for (PowerUpsEnum powerUp : powerUpsEnums) {
            TextButton txtButton = new TextButton(powerUp.name(), textButtonStyle);
            setActorUiObject(powerUp,txtButton.getClass().getName(), "Power Up Button", txtButton);
            powerUpsButtons.add(txtButton);
            this.table.add(txtButton);
        }
    }

    public void setActorUiObject(PowerUpsEnum name, String type, String tag, Actor actor){
        actor.setUserObject(new UiDataObject(name, type, tag));
    }

    public void clearButtonsList(){
        for (TextButton txtButton : this.powerUpsButtons) this.table.removeActor(txtButton);
        this.powerUpsButtons.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
