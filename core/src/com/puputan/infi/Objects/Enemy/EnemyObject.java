package com.puputan.infi.Objects.Enemy;

import Screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Objects.BaseObject;
import com.puputan.infi.Objects.BulletObject;
import com.puputan.infi.Objects.ExperiencePointObject;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;

public class EnemyObject extends BaseObject {

    private int hp = 1;
    private final String tag = "Enemy";
    private final float velocity = 150;
    private final Body body;
    private EnemyType enemyType;
    private Vector2 initialPosition;

    public EnemyObject(EnemyType enemyType, Vector2 position){
        super(AssetsRepository.enemyTexture);
        GameScreen.stage.addActor(this);
        this.enemyType = enemyType;
        this.initialPosition = position;
        body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, new Vector2(this.initialPosition.x,
                this.initialPosition.y));
        body.setUserData(this);

        this.setPosition(initialPosition.x, initialPosition.y);
        this.setSize(this.getWidth()*0.25f, this.getHeight()*0.25f);
    }

    public void act(float delta) {
        validateOutPosition();
        Vector2 actualPosition = new Vector2(this.getX(), this.getY());
        Vector2 newPosition;

        switch (enemyType){
            case Standard:
                this.setY(MovementUtils.moveVertical(actualPosition, false, velocity));
                break;
            case Follower:
                newPosition = MovementUtils.moveTowardsPoint(actualPosition,
                        new Vector2(GameScreen.playerObject.getX(), GameScreen.playerObject.getY()), velocity);
                this.setPosition(newPosition.x, newPosition.y);
                break;
            case Sinus:
                newPosition = MovementUtils.moveSinusoidal(actualPosition, 5, 10, velocity, 25);
                this.setPosition(newPosition.x, newPosition.y);
                break;
        }
        this.body.setTransform(this.getX(), this.getY(), 0);
    }

    public void hit(){
        this.hp--;
        if(this.hp <= 0){
            this.addToDispose(this.body);
        }
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof BulletObject || object instanceof PlayerObject){
            hit();
        }
    }

    public void validateOutPosition(){
        if(this.getY() < 0 - this.getHeight()) this.addToDispose(this.body);
    }

    public void onDestroy(){
        new ExperiencePointObject(new Vector2(this.getX(), this.getY()));
    }
}
