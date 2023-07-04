package com.puputan.infi.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import lombok.Getter;
import lombok.Setter;

public class MouseUtils {
    static int mousePositionX;
    static int mousePositionY;

    public static int getMousePositionX() {
        return mousePositionX = Gdx.input.getX();
    }
    public static int getMousePositionY(){
        return mousePositionY = Gdx.input.getY();
    }

    public static boolean isMouseClicked(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            return true;
        }return false;
    }
}
