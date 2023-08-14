package com.puputan.infi.Objects.Enemy;


import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawner extends Actor {

    private final float minWidth = 100;
    private final float maxWidth = GameScreen.WIDTH - 100;
    private final float spawnDeltaTimeInSeconds = 3;
    private final EnemyType[] enemyTypes;
    private final Random rand;
    private float lastSpawnTime;
    private LinkedList<EnemyPositionDTO> enemyPositions;
    boolean waveOn = false;

    public EnemySpawner() {
        rand = new Random();
        this.enemyTypes = EnemyType.values();
        this.enemyPositions = new LinkedList<>();

        this.lastSpawnTime = GameScreen.gameTime;
        GameScreen.gameStage.addActor(this);
    }

    private void spawnRandomEnemy() {
        int randomNum = ThreadLocalRandom.current().nextInt((int) this.minWidth, (int) this.maxWidth + 1);
        new EnemyObject(getRandomEnemyType(), new Vector2(randomNum, GameScreen.HEIGHT));
        this.lastSpawnTime = GameScreen.gameTime;
    }

    private EnemyType getRandomEnemyType() {
        return this.enemyTypes[rand.nextInt(enemyTypes.length)];
    }

    @Override
    public void act(float delta) {
        if(!waveOn) {
            spawnPositionersWave(12);
            waveOn = true;
        }if(waveOn && GameScreen.enemiesList.isEmpty()){
            waveOn = false;
            enemyPositions.clear();
        }
        //        if(GameScreen.gameTime - this.lastSpawnTime >= spawnDeltaTimeInSeconds )
        //            spawnRandomEnemy();
    }

    private void spawnPositionersWave(int enemyPositionersCount) {
        for (int i = 0; i < enemyPositionersCount; i++) {
            EnemyObject enemyObject = new EnemyObject(
                    EnemyType.Positioner, new Vector2(GameScreen.WIDTH / 2, GameScreen.HEIGHT));
            Vector2 wavePosition = countEnemyWavePosition(enemyObject);
            if(wavePosition == null) {
                enemyObject.addToDispose(enemyObject.getBody());
                return;
            }
            enemyObject.setTargetPosition(wavePosition);
        }
    }

    private Vector2 countEnemyWavePosition(EnemyObject enemyObject){
        Vector2 resultVector;

        float marginHorizontal = GameScreen.WIDTH*0.05f;
        float marginVertical = GameScreen.HEIGHT*0.05f;
        float marginDown = GameScreen.HEIGHT*0.4f;

        if(enemyPositions.isEmpty())
            resultVector = new Vector2(marginHorizontal, Gdx.graphics.getHeight() - marginVertical - enemyObject.getHeight());
        else {
            EnemyPositionDTO lastEnemyPosition = this.enemyPositions.getLast();
            float y = lastEnemyPosition.getPosition().y;
            float x = lastEnemyPosition.getPosition().x + lastEnemyPosition.getEnemyObject().getWidth() + marginHorizontal*2;
            if(x >= Gdx.graphics.getWidth()){
                y -= lastEnemyPosition.getEnemyObject().getHeight() + marginVertical;
                x = marginHorizontal;
            }
            if(y<=marginDown) return null;

            resultVector = new Vector2(x,y);
        }

        this.enemyPositions.add(new EnemyPositionDTO(resultVector, enemyObject));
        return resultVector;
    }
}