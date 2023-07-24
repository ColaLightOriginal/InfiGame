package com.puputan.infi.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import lombok.Getter;

public class RaycastCallbackImpl implements RayCastCallback {

    @Getter
    private Fixture returnFixture;

    public RaycastCallbackImpl(){
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(!(fixture.getBody().getUserData() instanceof EnemyObject)) return -1;
        else {
            this.returnFixture = fixture;
            return fraction;
        }
    }
}
