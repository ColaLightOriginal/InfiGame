package com.puputan.infi.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.puputan.infi.InfiGame;

import java.awt.*;

public abstract class BaseObject extends Image {

    public BaseObject(Texture texture){
        super(texture);
    }

    public abstract void onCollision(Fixture fixture);
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

    public void disposeObject(Body body){
        body.getFixtureList().clear();
        body = null;
        remove();
    }
}
