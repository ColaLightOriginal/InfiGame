package com.puputan.infi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.BulletObject;
import com.puputan.infi.Objects.EnemyObject;
import com.puputan.infi.Objects.PlayerObject;
import com.puputan.infi.Processors.GameInputProcessor;

import java.util.ArrayList;

public class InfiGame extends ApplicationAdapter {

	public final static float WIDTH = 1920;
	public final static float HEIGHT = 1080;

	private GameInputProcessor gameInputProcessor;
	public static ArrayList<BulletObject> bulletsList;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private PlayerObject playerObject;
	private EnemyObject enemyObject;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		playerObject = new PlayerObject(0,Gdx.graphics.getHeight(), AssetsRepository.playerTexture);
		enemyObject = new EnemyObject(AssetsRepository.enemyTexture);

		bulletsList = new ArrayList<>();

		this.gameInputProcessor = new GameInputProcessor(this.playerObject);
		Gdx.input.setInputProcessor(this.gameInputProcessor);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		playerObject.update(batch);
		enemyObject.update(batch);

		for (BulletObject bullet: bulletsList) {
			if(bullet.isObjectOutOfCamera(bullet.getSprite())) {
				bullet=null;
				continue;
			}
			bullet.update(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
