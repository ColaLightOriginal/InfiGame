package com.puputan.infi.Utils;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Objects.BaseObject;

public class BodyUtils {

    public static Body defineBody(BodyDef.BodyType bodyType, BaseObject baseObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(baseObject.getX() + baseObject.getWidth()/2, baseObject.getY() + baseObject.getHeight()/2);
        Body body = GameScreen.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(baseObject.getWidth()/2);
        circle.setPosition(new Vector2(baseObject.getX() + baseObject.getWidth()/2, baseObject.getY() + baseObject.getHeight()/2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        Fixture fixture = body.createFixture(fixtureDef);
        return body;
    }
}