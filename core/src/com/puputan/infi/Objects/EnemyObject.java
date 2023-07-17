package com.puputan.infi.Objects;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;

public class EnemyObject extends BaseObject {

    private int hp = 1;
    private final String tag = "Enemy";
    private final float velocity = 150;
    private final Body body;

    public EnemyObject(){
        super(AssetsRepository.enemyTexture);
        GameScreen.stage.addActor(this);
        body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, new Vector2(this.getX(), this.getY()));
        body.setUserData(this);

        this.setPosition(GameScreen.WIDTH/2-this.getWidth()/2, GameScreen.HEIGHT);
        this.setSize(this.getWidth()*0.25f, this.getHeight()*0.25f);
    }

    public void act(float delta) {
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), false, velocity));
        this.body.setTransform(this.getX(), this.getY(), 0);
    }

    public void hit(){
        this.hp--;
        if(this.hp <= 0) this.addToDispose(this.body);
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof BulletObject || object instanceof PlayerObject){
            hit();
        }
    }

    public void onDestroy(){
        new ExperiencePointObject(new Vector2(this.getX(), this.getY()));
    }
}
