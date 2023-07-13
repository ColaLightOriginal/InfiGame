package com.puputan.infi.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;

public class EnemyObject extends BaseObject {

    private String tag = "Enemy";
    private final float velocity = 150;
    private Body body;

    public EnemyObject(){
        super(AssetsRepository.enemyTexture);
        InfiGame.stage.addActor(this);
        body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, new Vector2(this.getX(), this.getY()));
        body.setUserData(this);

        this.setPosition(InfiGame.WIDTH/2-this.getWidth()/2, InfiGame.HEIGHT);
        this.setSize(this.getWidth()*0.25f, this.getHeight()*0.25f);
    }

    public void act(float delta) {
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), false, velocity));
        this.body.setTransform(this.getX(), this.getY(), 0);
    }

    @Override
    public void onCollision(Fixture fixture) {
        System.out.println("Enemy collided");
        disposeObject(this.body);
    }
}
