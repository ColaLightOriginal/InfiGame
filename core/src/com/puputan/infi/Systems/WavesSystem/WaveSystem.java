package com.puputan.infi.Systems.WavesSystem;

import java.util.LinkedList;

public class WaveSystem {
    public static boolean waveOn = false;
    public static PositionersWave positionersWave = new PositionersWave();

    private LinkedList<BaseWave> activeWaves = new LinkedList<>();

    public void checkIfWavesEnded(){
        if(activeWaves.isEmpty()) return;;
        for (BaseWave wave : activeWaves) {
            if(wave.isWaveEnded()) wave.endWave();
        }
    }
}
