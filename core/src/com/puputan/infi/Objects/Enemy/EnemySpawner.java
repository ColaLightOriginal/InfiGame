package com.puputan.infi.Objects.Enemy;


import Screens.GameScreen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.puputan.infi.InfiGame;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawner extends Actor {

    private final float minWidth = 100;
    private final float maxWidth = GameScreen.WIDTH - 100;
    private final EnemyType[] enemyTypes;
    private Random rand;
    private final float spawnDeltaTimeInSeconds;
    private float lastSpawnTime;
    private float actualTimeInSeconds;

    public EnemySpawner() {
        rand = new Random();
        this.enemyTypes = EnemyType.values();
        spawnDeltaTimeInSeconds = 3;
        this.lastSpawnTime = TimeUtils.nanoTime() * MathUtils.nanoToSec;
        GameScreen.stage.addActor(this);
    }

    private void spawnRandomEnemy() {
        int randomNum = ThreadLocalRandom.current().nextInt((int) this.minWidth, (int) this.maxWidth + 1);
        new EnemyObject(getRandomEnemyType(), new Vector2(randomNum, GameScreen.HEIGHT));
        this.lastSpawnTime = TimeUtils.nanoTime() * MathUtils.nanoToSec;
    }

    private EnemyType getRandomEnemyType() {
        return this.enemyTypes[rand.nextInt(enemyTypes.length)];
    }

    @Override
    public void act(float delta) {
        actualTimeInSeconds = TimeUtils.nanoTime() * MathUtils.nanoToSec;
        if((actualTimeInSeconds - lastSpawnTime) >=3 )
            spawnRandomEnemy();
    }
}