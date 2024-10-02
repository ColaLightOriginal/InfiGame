package com.puputan.infi.Objects.Enemy;


import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.puputan.infi.Systems.WavesSystem.WaveSystem;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawner extends Actor {

    private final float minWidth = 100;
    private final float maxWidth = GameScreen.WIDTH - 100;
    private final float spawnDeltaTimeInSeconds = 1;
    private final float spawnWaveDeltaTimeInSeconds = 5;
    private float randomDeltaWaveTime = 10;

    private float lastWaveSpawnEndTime;
    private float lastSpawnTime;
    private boolean sinsusoidSpawning = true;
    private int sinusoidsWaveCount = 12;
    private float lastSinusoidSpawnTime = 0;

    private final EnemyType[] enemyTypes;
    private final Random rand;
    private LinkedList<EnemyPositionDTO> enemyPositions;
    private SpawnerStateEnum spawnerState;

    public EnemySpawner() {
        rand = new Random();
        this.enemyTypes = EnemyType.values();
        this.enemyPositions = new LinkedList<>();

        this.lastSpawnTime = GameScreen.gameTime;
        this.lastWaveSpawnEndTime = GameScreen.gameTime;

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

    private float getRandomWaveDeltaTime(){
        return rand.nextInt(10);
    }

    @Override
    public void act(float delta) {

        float endOfLastWaveTime = GameScreen.gameTime - this.lastWaveSpawnEndTime;
        float lastEnemySpawnTime = GameScreen.gameTime - this.lastSpawnTime;

        if(WaveSystem.waveOn && sinsusoidSpawning) spawnSinusoidWave();
        if(!WaveSystem.waveOn && (endOfLastWaveTime >= spawnWaveDeltaTimeInSeconds + randomDeltaWaveTime)) {
            WaveSystem.positionersWave.createWave(12);
            if(!spawnSinusoidWave()) WaveSystem.waveOn = false;
        }else if(WaveSystem.waveOn && GameScreen.enemiesList.isEmpty()){
            WaveSystem.waveOn = false;
            enemyPositions.clear();
            lastWaveSpawnEndTime = GameScreen.gameTime;
            randomDeltaWaveTime = getRandomWaveDeltaTime();
        }else if(!WaveSystem.waveOn){
            if(lastEnemySpawnTime >= spawnDeltaTimeInSeconds )
                spawnRandomEnemy();
        }
    }

    private boolean spawnSinusoidWave(){
        float lastSpawnTime = GameScreen.gameTime - lastSinusoidSpawnTime;
        if(sinusoidsWaveCount <=0) {
            sinsusoidSpawning = false;
            return false;
        }
        else if (lastSpawnTime > 25) {
            new EnemyObject(EnemyType.Sinus, new Vector2(GameScreen.WIDTH / 2, GameScreen.HEIGHT));
            sinusoidsWaveCount--;
            lastSinusoidSpawnTime = GameScreen.gameTime;
            return true;
        }
        return true;
    }
}