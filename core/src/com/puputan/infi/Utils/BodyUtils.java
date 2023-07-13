package com.puputan.infi.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.puputan.infi.InfiGame;

public class BodyUtils {

    public static Body defineBody(BodyDef.BodyType bodyType, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.x, position.y);
        Body body = InfiGame.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        Fixture fixture = body.createFixture(fixtureDef);
        return body;
    }
}
