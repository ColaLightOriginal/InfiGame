package Screens;

import Screens.Stages.PowerUpChooseUIStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Objects.Bullet.BulletObject;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.Enemy.EnemySpawner;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Tools.ContactListener;
import com.puputan.infi.Tools.GameInputProcessor;

import java.util.ArrayList;

public class GameScreen implements Screen {

    public final static float WIDTH = 414;
    public final static float HEIGHT = 896;
    public static Stage gameStage;
    public static PowerUpChooseUIStage powerUpChooseUIStage;
    public static World world;

    private final Box2DDebugRenderer debugRenderer;
    public static ArrayList<BulletObject> bulletsList;
    public static ArrayList<EnemyObject> enemiesList;
    public static PlayerObject playerObject;
    public static EnemySpawner enemySpawner;

    public static Array<Body> bodiesToDestroy;

    private final OrthographicCamera camera;
    public static GameStatesEnum gameStates;
    private GameInputProcessor gameInputProcessor;

    public static float gameTime;
    public static float startTime;
    public static float pauseTime;

    public GameScreen(InfiGame game){
        startTime = TimeUtils.nanoTime() * MathUtils.nanoToSec;
        gameTime = 0f;
        pauseTime = 0f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        Viewport screenViewport = new ScreenViewport(camera);

        gameStage = new Stage(screenViewport);
        powerUpChooseUIStage = new PowerUpChooseUIStage(screenViewport);

        world = new World(new Vector2(0,0), true);
        debugRenderer = new Box2DDebugRenderer();
        ContactListener contactListener = new ContactListener();
        world.setContactListener(contactListener);

        bodiesToDestroy = new Array<>();
        playerObject = new PlayerObject();
        enemySpawner = new EnemySpawner();
        bulletsList = new ArrayList<>();
        enemiesList = new ArrayList<>();

        gameStates = GameStatesEnum.Running;

        gameInputProcessor = new GameInputProcessor(playerObject, this);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        switch (gameStates){
            case Running:
                if(pauseTime>0){
                    pauseTime = TimeUtils.nanoTime() * MathUtils.nanoToSec - pauseTime;
                    startTime += pauseTime;
                }
                pauseTime = 0;
                gameTime = (TimeUtils.nanoTime() * MathUtils.nanoToSec)-startTime;
                Gdx.input.setInputProcessor(gameInputProcessor);
                ScreenUtils.clear(0, 0, 0, 1);

                gameStage.act(Gdx.graphics.getDeltaTime());
                gameStage.draw();

                debugRenderer.render(world, camera.combined);
                world.step(1/60f, 6, 2);
                clearBodies();
                break;
            case Paused:
                ScreenUtils.clear(0, 0, 0, 1);
                break;
            case PowerUpChoose:
                Gdx.input.setInputProcessor(powerUpChooseUIStage);
                ScreenUtils.clear(0, 0, 0, 1);

                powerUpChooseUIStage.act(Gdx.graphics.getDeltaTime());
                powerUpChooseUIStage.draw();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if(gameStates == GameStatesEnum.Running){
            gameStates = GameStatesEnum.Paused;
            pauseTime = TimeUtils.nanoTime() * MathUtils.nanoToSec;
        }
        else gameStates = GameStatesEnum.Running;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        AssetsRepository.disposeAssets();
        gameStage.dispose();
    }

    public void clearBodies(){
        if(bodiesToDestroy.size == 0) return;
        for (Body body: bodiesToDestroy) {
            if(body.getUserData() instanceof EnemyObject) {
                EnemyObject eo = (EnemyObject) body.getUserData();
                eo.onDestroy();
            }
            body.getFixtureList().clear();
            world.destroyBody(body);
            body = null;
        }
        bodiesToDestroy.clear();
    }
}
