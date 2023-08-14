package com.puputan.infi.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.puputan.infi.Objects.BaseObject;

public class MovementUtils {

    private static final float dashDistance = Gdx.graphics.getWidth()*0.1f;

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

    public static float moveHorizontal(Vector2 actualPosition, boolean isRight, float velocity){
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

    public static float getDistanceBetweenObjects(BaseObject objectA, BaseObject objectB){
        return (float) Math.sqrt (Math.pow(objectB.getX() - objectA.getX(),2) + Math.pow(objectB.getY() - objectA.getY(),2));
    }

    public static float getDistanceBetweenPoints(Vector2 positionA, Vector2 positionB){
        return (float) Math.sqrt (Math.pow(positionB.x - positionA.x,2) + Math.pow(positionB.y - positionA.y,2));
    }

    public static Vector2 dashToPosition(Vector2 actualPosition, Vector2 destinedPosition){
        float distanceBetweenPoints = getDistanceBetweenPoints(actualPosition, destinedPosition);
        Vector2 direction = actualPosition.cpy().sub(destinedPosition).nor();
        direction.set(-direction.x, -direction.y);

        if(distanceBetweenPoints > dashDistance) actualPosition.add(direction.scl(dashDistance));
        else actualPosition.add(direction.scl(distanceBetweenPoints - dashDistance));

        return actualPosition;
    }
}
