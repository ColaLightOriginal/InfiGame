package com.puputan.infi.Processors;
import Screens.GameScreen;
import Screens.GameStates;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.puputan.infi.Objects.Player.PlayerObject;

public class GameInputProcessor implements InputProcessor {

    private final PlayerObject playerObject;
    private final GameScreen screen;

    public GameInputProcessor(PlayerObject playerObject, GameScreen screen) {
        this.playerObject = playerObject;
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.ESCAPE:
                this.screen.pause();
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button){
            case Input.Buttons.LEFT:
                this.playerObject.getPlayerSystems().shoot();
                return true;
            case Input.Buttons.RIGHT:
                this.playerObject.dash();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
