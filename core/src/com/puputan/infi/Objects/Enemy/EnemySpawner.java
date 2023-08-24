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
    private final float spawnDeltaTimeInSeconds = 1;
    private final float spawnWaveDeltaTimeInSeconds = 5;
    private final float spawnSinusWaveDeltaTimeInSeconds = 3;

    private float lastWaveSpawnEndTime;
    private float lastSpawnTime;
    private float lastSinusWaveSpawnEndTime;

    private final EnemyType[] enemyTypes;
    private final Random rand;
    private LinkedList<EnemyPositionDTO> enemyPositions;
    boolean waveOn = false;
    boolean sinusWaveOn = false;
    private SpawnerStateEnum spawnerState;

    public EnemySpawner() {
        rand = new Random();
        this.enemyTypes = EnemyType.values();
        this.enemyPositions = new LinkedList<>();

        this.lastSpawnTime = GameScreen.gameTime;
        this.lastWaveSpawnEndTime = GameScreen.gameTime;
        this.lastSinusWaveSpawnEndTime = GameScreen.gameTime;

        this.spawnerState = SpawnerStateEnum.Idle;

        GameScreen.gameStage.addActor(this);
    }

    private void spawnRandomEnemy() {
        int randomNum = ThreadLocalRandom.current().nextInt((int) this.minWidth, (int) this.maxWidth + 1);
        new EnemyObject(getRandomEnemyType(), new Vector2(randomNum, GameScreen.HEIGHT));
        this.lastSpawnTime = GameScreen.gameTime;
    }

    private EnemyType getRandomEnemyType() {
        return this.enemyTypes[rand.nextInt(enemyTypes.length-1)];
    }

    @Override
    public void act(float delta) {

//        switch (spawnerState){
//            case Idle:
//                break;
//            case Normal:
//                spawnRandomEnemy();
//            case PositionersWave:
//                spawnPositionersWave(12);
//                break;
//            case SinusWave:
//                spawnSinusoidWave(6);
//                break;
//        }

//        if(!waveOn && (GameScreen.gameTime - this.lastSinusWaveSpawnEndTime >= spawnSinusWaveDeltaTimeInSeconds)) {
//            spawnSinusoidWave(6);
//            waveOn = true;
        if(!waveOn && (GameScreen.gameTime - this.lastWaveSpawnEndTime >= spawnWaveDeltaTimeInSeconds)) {
//            spawnPositionersWave(12);
            spawnSinusoidWave(6);
            waveOn = true;
        }else if(waveOn && GameScreen.enemiesList.isEmpty()){
            waveOn = false;
            enemyPositions.clear();
            lastWaveSpawnEndTime = GameScreen.gameTime;
        }else if(!waveOn){
            if(GameScreen.gameTime - this.lastSpawnTime >= spawnDeltaTimeInSeconds )
                spawnRandomEnemy();
        }
    }

    private void spawnPositionersWave(int enemyPositionersCount) {
        for (int i = 0; i < enemyPositionersCount; i++) {
            EnemyObject enemyObject = new EnemyObject(
                    EnemyType.Positioner, new Vector2(GameScreen.WIDTH / 2, GameScreen.HEIGHT));
            Vector2 wavePosition = calculateEnemyWavePosition(enemyObject);
            if(wavePosition == null) {
                enemyObject.destroy();
                return;
            }
            enemyObject.setTargetPosition(wavePosition);
        }
    }

    private void spawnSinusoidWave(int enemySinusoidsCound){
        for(int i = 0; i < enemySinusoidsCound; i++){
            EnemyObject enemyObject = new EnemyObject(
                    EnemyType.Sinus, new Vector2(GameScreen.WIDTH/2, GameScreen.HEIGHT));
        }
    }
    private Vector2 calculateEnemyWavePosition(EnemyObject enemyObject){
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

//    public void calculateSpawnerState(){
//        if(GameScreen.gameTime < 3) spawnerState = SpawnerStateEnum.Idle;
//        else if(!waveOn && GameScreen.gameTime - this.lastSpawnTime >= lastWaveSpawnEndTime) {
//            waveOn = true;
//        }//        if(waveOn && GameScreen.enemiesList.isEmpty()){
//            waveOn = false;
//            enemyPositions.clear();
//            lastWaveSpawnEndTime = GameScreen.gameTime;
//        }
//
//        else spawnerState = SpawnerStateEnum.Normal;
//    }
}