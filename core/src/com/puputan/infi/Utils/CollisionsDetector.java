package com.puputan.infi.Utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CollisionsDetector {

    public static boolean checkCollision(Sprite firstSprite, Sprite secondSprite){
        return firstSprite.getBoundingRectangle().overlaps(secondSprite.getBoundingRectangle());
    }
}
