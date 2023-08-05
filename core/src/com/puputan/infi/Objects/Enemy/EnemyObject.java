package com.puputan.infi.Objects.Enemy;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.BaseObject;
import com.puputan.infi.Objects.Bullet.BulletObject;
import com.puputan.infi.Objects.ExperiencePointObject;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.MovementUtils;

public class EnemyObject extends BaseObject {

    private int hp = 1;
    private final float velocity = 150;
    private EnemyType enemyType;
    private boolean addedToDispose;

    public EnemyObject(EnemyType enemyType, Vector2 position){
        super(AssetsRepository.enemyTexture);
        GameScreen.gameStage.addActor(this);
        this.enemyType = enemyType;

        this.setPosition(position.x, position.y);
        this.getBody().setTransform(this.getX(), this.getY(),0);
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
        this.getBody().setTransform(this.getX(), this.getY(), 0);
    }

    public void hit(){
        this.hp--;
        if(this.hp <= 0 && !this.addedToDispose){
            this.addedToDispose = true;
            this.addToDispose(this.getBody());
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
        if(this.getY() < 0 - this.getHeight() && !this.addedToDispose) {
            this.addedToDispose = true;
            this.addToDispose(this.getBody());
        }
    }

    public void onDestroy(){
        new ExperiencePointObject(new Vector2(this.getX(), this.getY()));
    }
}
