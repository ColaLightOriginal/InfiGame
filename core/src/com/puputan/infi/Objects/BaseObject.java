package com.puputan.infi.Objects;

import Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.puputan.infi.InfiGame;

public abstract class BaseObject extends Image {

    public BaseObject(Texture texture){
        super(texture);
    }

    public abstract void onCollision(Fixture fixture);
    public abstract void act(float delta);
    public void scaleSize(float scaleValue, Sprite sprite){
        sprite.setSize(sprite.getWidth()*scaleValue, sprite.getHeight()*scaleValue);
    }

    public boolean isObjectOutOfCamera(Sprite sprite){
        if(sprite.getX() > GameScreen.WIDTH) return true;
        else if(sprite.getX() + sprite.getWidth() < 0) return true;
        else if(sprite.getY() > GameScreen.HEIGHT) return true;
        else if(sprite.getY() + sprite.getHeight() < 0) return true;
        return false;
    }

    public void addToDispose(Body body){
        BaseObject bo = (BaseObject) body.getUserData();
        bo.remove();
        GameScreen.bodiesToDestroy.add(body);
    }

    public void createBody(BodyDef bodyDef){
        GameScreen.world.createBody(bodyDef);
    }

    public void createObject(){

    }
}
