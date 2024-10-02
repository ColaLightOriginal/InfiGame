package com.puputan.infi.Systems.WavesSystem;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.Enemy.EnemyPositionDTO;
import com.puputan.infi.Objects.Enemy.EnemyType;

import java.util.LinkedList;


public class PositionersWave extends BaseWave implements WaveInterface{

    private LinkedList<EnemyPositionDTO> enemyPositions;

    @Override
    public void createWave(int enemiesCount) {
        enemyPositions = new LinkedList<>();
        WaveSystem.waveOn = true;

        for (int i = 0; i < enemiesCount; i++) {
            EnemyObject enemyObject = new EnemyObject(
                    EnemyType.Positioner, new Vector2(GameScreen.WIDTH / 2, GameScreen.HEIGHT));
            Vector2 wavePosition = calculateEnemyWavePosition(enemyObject, enemyPositions);
            if(wavePosition == null) {
                enemyObject.destroy();
                return;
            }
            enemyObject.setTargetPosition(wavePosition);
        }
    }

    @Override
    public void endWave() {
        WaveSystem.waveOn = false;
        this.enemyPositions.clear();
    }

    @Override
    public boolean isWaveEnded() {
        return this.enemyPositions.isEmpty();
    }

    private Vector2 calculateEnemyWavePosition(EnemyObject enemyObject, LinkedList<EnemyPositionDTO> enemyPositions){

        Vector2 resultVector;

        float marginHorizontal = GameScreen.WIDTH*0.05f;
        float marginVertical = GameScreen.HEIGHT*0.05f;
        float marginDown = GameScreen.HEIGHT*0.4f;

        if(enemyPositions.isEmpty())
            resultVector = new Vector2(marginHorizontal, Gdx.graphics.getHeight() - marginVertical - enemyObject.getHeight());
        else {
            EnemyPositionDTO lastEnemyPosition = enemyPositions.getLast();
            float y = lastEnemyPosition.getPosition().y;
            float x = lastEnemyPosition.getPosition().x + lastEnemyPosition.getEnemyObject().getWidth() + marginHorizontal*2;
            if(x >= Gdx.graphics.getWidth()){
                y -= lastEnemyPosition.getEnemyObject().getHeight() + marginVertical;
                x = marginHorizontal;
            }
            if(y<=marginDown) return null;

            resultVector = new Vector2(x,y);
        }

        enemyPositions.add(new EnemyPositionDTO(resultVector, enemyObject));
        return resultVector;
    }
}
