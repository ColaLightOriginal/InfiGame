package com.puputan.infi.Processors;
import Screens.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.puputan.infi.Objects.Player.PlayerObject;

public class GameInputProcessor implements InputProcessor {

    private final PlayerObject playerObject;

    public GameInputProcessor(PlayerObject playerObject){
        this.playerObject = playerObject;
    }

    @Override
    public boolean keyDown(int keycode) {
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
                GameScreen.bulletsList.addAll(this.playerObject.getPlayerFuctions().shoot());
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
