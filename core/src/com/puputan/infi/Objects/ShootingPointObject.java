package com.puputan.infi.Objects;

import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Objects.Player.PlayerObject;
import lombok.Getter;

@Getter
public class ShootingPointObject {

    private Vector2 position;
    private Vector2 offset;

    public ShootingPointObject(Vector2 offset, PlayerObject playerObject){
        this.offset = offset;
        setPosition(offset, playerObject);
    }
    public void updatePosition(PlayerObject playerObject){
        this.position = setPosition(this.offset, playerObject);
    }

    private Vector2 setPosition(Vector2 offset, PlayerObject playerObject){
        return new Vector2(playerObject.getX() + offset.x,
                playerObject.getY() + offset.y);
    }
}
