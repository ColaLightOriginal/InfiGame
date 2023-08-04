package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Tools.ContactListener;
import com.puputan.infi.Objects.Bullet.BulletObject;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.Enemy.EnemySpawner;
import com.puputan.infi.Objects.Player.PlayerObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class GameScreen implements Screen {

    public final static float WIDTH = 414;
    public final static float HEIGHT = 896;
    public static Stage stage;
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    public static ArrayList<BulletObject> bulletsList;
    private OrthographicCamera camera;
    public static Array<Body> bodiesToDestroy;
    public static PlayerObject playerObject;
    public static EnemySpawner enemySpawner;

    private final InfiGame game;

    private GameStates gameStates;

    public GameScreen(InfiGame game){

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        stage = new Stage(new ScreenViewport(camera));
        world = new World(new Vector2(0,0), true);
        ContactListener contactListener = new ContactListener();
        world.setContactListener(contactListener);

        bodiesToDestroy = new Array<>();

        debugRenderer = new Box2DDebugRenderer();

        playerObject = new PlayerObject();
        enemySpawner = new EnemySpawner();

        bulletsList = new ArrayList<>();

        this.gameStates=GameStates.Running;

        com.puputan.infi.Processors.GameInputProcessor gameInputProcessor =
                new com.puputan.infi.Processors.GameInputProcessor(playerObject, this);
        Gdx.input.setInputProcessor(gameInputProcessor);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        switch (this.gameStates){
            case Running:
                ScreenUtils.clear(0, 0, 0, 1);

                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();

                debugRenderer.render(world, camera.combined);
                world.step(1/60f, 6, 2);
                clearBodies();
                break;
            case Paused:
                ScreenUtils.clear(0, 0, 0, 1);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if(this.gameStates == GameStates.Running)
            this.gameStates = GameStates.Paused;
        else this.gameStates = GameStates.Running;
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
