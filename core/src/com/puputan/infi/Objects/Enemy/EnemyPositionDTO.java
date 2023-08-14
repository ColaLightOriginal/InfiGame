package com.puputan.infi.Objects.Enemy;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

@Getter
public class EnemyPositionDTO {

    private final Vector2 position;
    private final EnemyObject enemyObject;

    public EnemyPositionDTO(Vector2 position, EnemyObject enemyObject){
        this.position = position;
        this.enemyObject = enemyObject;
    }
}
