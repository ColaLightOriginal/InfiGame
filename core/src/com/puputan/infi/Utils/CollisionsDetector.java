package com.puputan.infi.Utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class CollisionsDetector {

    public static boolean isRectangleCollisionDetected(Rectangle rectangleA, Rectangle rectangleB){
        return rectangleA.overlaps(rectangleB);
    }

    public static boolean isCircleCollisionDetected(Circle circleA, Circle circleB){
        return circleA.overlaps(circleB);
    }
}
