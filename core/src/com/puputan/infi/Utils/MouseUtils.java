package com.puputan.infi.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
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

    public static Vector2 getMousePositionVector(){
        return new Vector2(getMousePositionX(), Gdx.graphics.getHeight() - getMousePositionY());
    }
    public static boolean isMouseClicked(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            return true;
        }return false;
    }
}
