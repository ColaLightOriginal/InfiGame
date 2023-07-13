package com.puputan.infi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.puputan.infi.Listeners.ContactListener;
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
	public static Stage stage;
	public static World world;
	private Box2DDebugRenderer debugRenderer;
	private GameInputProcessor gameInputProcessor;
	public static ArrayList<BulletObject> bulletsList;
	private OrthographicCamera camera;
	private PlayerObject playerObject;
	private EnemyObject enemyObject;
	public LinkedList<Objects> actualObjects = new LinkedList<>();


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		stage = new Stage(new FitViewport(WIDTH,HEIGHT, camera));
		world = new World(new Vector2(0,0), true);
		ContactListener contactListener = new ContactListener();
		world.setContactListener(contactListener);

		debugRenderer = new Box2DDebugRenderer();

		playerObject = new PlayerObject();
		enemyObject = new EnemyObject();

		bulletsList = new ArrayList<>();

		this.gameInputProcessor = new GameInputProcessor(this.playerObject);
		Gdx.input.setInputProcessor(this.gameInputProcessor);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
	}
}
