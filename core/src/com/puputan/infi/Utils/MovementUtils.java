package com.puputan.infi.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

public class MovementUtils {

    private static float time = Gdx.graphics.getDeltaTime();

    public static Vector2 moveTowardsPoint(Vector2 actualPosition, Vector2 targetPosition, float velocity) {
        float distance = actualPosition.dst(targetPosition);
        float movementAmount = velocity * Gdx.graphics.getDeltaTime();

        if (distance > movementAmount) {
            float lerpAmount = movementAmount / distance;
            return actualPosition.lerp(targetPosition, lerpAmount);
        } else {
            return targetPosition.cpy();
        }
    }

    public static float moveHorizontal(Vector2 actualPosition ,boolean isRight, float velocity){
        if(isRight) return actualPosition.x += velocity*Gdx.graphics.getDeltaTime();
        return actualPosition.x -= velocity*Gdx.graphics.getDeltaTime();
    }

    public static float moveVertical(Vector2 actualPosition, boolean isUp, float velocity){
        if(isUp) return actualPosition.y+=velocity*Gdx.graphics.getDeltaTime();
        return actualPosition.y -= velocity*Gdx.graphics.getDeltaTime();
    }

    public static Vector2 moveSinusoidal(Vector2 actualPosition, float frequency, float offset, float velocity, float amplitudeX){
        float time = TimeUtils.nanoTime() * MathUtils.nanoToSec;
        actualPosition.x += MathUtils.sin((time + offset) * frequency) * amplitudeX;
        actualPosition.y -= velocity * Gdx.graphics.getDeltaTime();

        return actualPosition;
    }
}
