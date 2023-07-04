package com.puputan.infi.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public class ShootingPointObject {

    @Getter
    private Vector2 position;

    public ShootingPointObject(PlayerObject playerObject){
        setPosition(playerObject);
    }
    public void updatePosition(PlayerObject playerObject){
        this.position = setPosition(playerObject);
    };

    private Vector2 setPosition(PlayerObject playerObject){
        return new Vector2(playerObject.getSprite().getX() + playerObject.getSprite().getWidth()/2,
                playerObject.getSprite().getY() + playerObject.getSprite().getHeight() + 1);
    }
}
