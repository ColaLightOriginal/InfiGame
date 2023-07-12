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
import java.util.LinkedList;
import java.util.Objects;

public class InfiGame extends ApplicationAdapter {

	public final static float WIDTH = 1920;
	public final static float HEIGHT = 1080;
	public static SpriteBatch spriteBatch;
	private GameInputProcessor gameInputProcessor;
	public static ArrayList<BulletObject> bulletsList;
	private OrthographicCamera camera;
	private PlayerObject playerObject;
	private EnemyObject enemyObject;
	public LinkedList<Objects> actualObjects = new LinkedList<>();

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		playerObject = new PlayerObject();
		enemyObject = new EnemyObject();

		bulletsList = new ArrayList<>();

		this.gameInputProcessor = new GameInputProcessor(this.playerObject);
		Gdx.input.setInputProcessor(this.gameInputProcessor);
	}

	@Override
	public void render () {
		ScreenUtils.clear(256, 256, 256, 1);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		playerObject.update();
		enemyObject.update();

		for (BulletObject bullet: bulletsList) {
			if(bullet.isObjectOutOfCamera(bullet.getSprite())) {
				bullet=null;
				continue;
			}
			bullet.update();
		}
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
