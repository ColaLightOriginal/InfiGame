package com.puputan.infi.Objects.Bullet;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.BaseObject;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.ShootingPointObject;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

@Getter
public class BulletObject extends BaseObject {

    private final float velocity = 600;
    private final float offset = 120;

    private final ShootingPointType shootingPointType;

    public BulletObject(ShootingPointObject shootingPointObject, ShootingPointType shootingPointType){
        super(AssetsRepository.bulletTexture);

        this.setPosition(shootingPointObject.getPosition().x, shootingPointObject.getPosition().y);
        this.getBody().setTransform(this.getX(), this.getY(),0);
        this.shootingPointType = shootingPointType;
    }

    public void act(float delta){
        validateOutPosition();
        switch (shootingPointType){
            case MIDDLE:
                break;
            case LEFT:
                this.setX(MovementUtils.moveHorizontal(new Vector2(this.getX(), this.getY()), false, this.offset));
                break;
            case RIGHT:
                this.setX(MovementUtils.moveHorizontal(new Vector2(this.getX(), this.getY()), true, this.offset));
                break;
        }
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), true, velocity));
        this.getBody().setTransform(this.getX(), this.getY(), 0);
    }

    public void validateOutPosition(){
        if(this.getY() > (GameScreen.HEIGHT + this.getHeight()) && !this.isAddedToDispose()){
            this.setAddedToDispose(true);
            this.addToDispose(this.getBody());
        }
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof EnemyObject && !this.isAddedToDispose()) {
            this.setAddedToDispose(true);
            this.addToDispose(this.getBody());
        }
    }
}
