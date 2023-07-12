package com.puputan.infi.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.puputan.infi.InfiGame;

import java.awt.*;

public abstract class BaseObject {

    public abstract void update();
    public abstract void onCollisionDetection();

    public void draw(Sprite sprite){
        sprite.draw(InfiGame.spriteBatch);
    };

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
