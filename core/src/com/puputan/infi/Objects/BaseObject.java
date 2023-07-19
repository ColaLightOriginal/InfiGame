package com.puputan.infi.Objects;

import Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.puputan.infi.Utils.BodyUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class BaseObject extends Image {

    @Setter
    private boolean isAddedToDispose;
    private Body body;

    public BaseObject(Texture texture){
        super(texture);
        GameScreen.stage.addActor(this);
        this.setSize(this.getWidth()*0.25f, this.getHeight()*0.25f);
        body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, this);
        body.setUserData(this);
        isAddedToDispose = false;
    }

    public abstract void onCollision(Fixture fixture);
    public abstract void act(float delta);
    public void scaleSize(float scaleValue, Sprite sprite){
        sprite.setSize(sprite.getWidth()*scaleValue, sprite.getHeight()*scaleValue);
    }

    public boolean isObjectOutOfCamera(BaseObject bo){
        if(bo.getX() > GameScreen.WIDTH) return true;
        else if(bo.getX() + bo.getWidth() < 0) return true;
        else if(bo.getY() > GameScreen.HEIGHT) return true;
        else if(bo.getY() + bo.getHeight() < 0) return true;
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
