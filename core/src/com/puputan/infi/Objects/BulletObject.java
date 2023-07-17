package com.puputan.infi.Objects;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

@Getter
public class BulletObject extends BaseObject {

    private final String tag = "Bullet";
    private final float velocity = 600;
    private Body body;

    public BulletObject(ShootingPointObject shootingPointObject){
        super(AssetsRepository.bulletTexture);
        GameScreen.stage.addActor(this);
        this.body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, new Vector2(this.getX(), this.getY()));
        this.body.setUserData(this);

        this.setSize(this.getWidth()*0.1f, this.getHeight()*0.1f);
        this.setPosition(shootingPointObject.getPosition().x - this.getWidth()/2, shootingPointObject.getPosition().y);
    }

    public void act(float delta){
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), true, velocity));
        this.body.setTransform(this.getX(), this.getY(), 0);
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof EnemyObject) this.addToDispose(this.body);
    }
}
