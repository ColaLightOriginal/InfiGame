package com.puputan.infi.Objects;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.puputan.infi.InfiGame;

public class BaseObject extends InputAdapter {

    public void scaleSize(float scaleValue, Sprite sprite){
        sprite.setSize(sprite.getWidth()*scaleValue, sprite.getHeight()*scaleValue);
    }

    public boolean isObjectOutOfCamera(Sprite sprite){
        if(sprite.getX() > InfiGame.WIDTH) return true;
        else if(sprite.getX() + sprite.getWidth() < 0) return true;
        else if(sprite.getY() > InfiGame.HEIGHT) return true;
        else if(sprite.getY() + sprite.getHeight() < 0) return true;
        return false;
    }
}
