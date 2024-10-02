package com.puputan.infi.Systems.WavesSystem;

import com.puputan.infi.Objects.Enemy.EnemyObject;

public interface WaveInterface {

    void createWave(int enemiesCount);
    void endWave();
    boolean isWaveEnded();
}
