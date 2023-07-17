package com.puputan.infi.Configurations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class AssetsRepository {
    public final static Texture playerTexture = new Texture(Gdx.files.internal("player_sprite.png"));
    public final static Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
    public final static Texture enemyTexture = new Texture(Gdx.files.internal("alien.png"));
    public final static Texture expTexture = new Texture(Gdx.files.internal("exp.png"));
    public static void disposeAssets(){
        playerTexture.dispose();
        bulletTexture.dispose();
        enemyTexture.dispose();
    }
}
