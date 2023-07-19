package com.puputan.infi.Objects;

import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

@Getter
public class BulletObject extends BaseObject {

    private final float velocity = 600;

    public BulletObject(ShootingPointObject shootingPointObject){
        super(AssetsRepository.bulletTexture);
        this.setSize(this.getWidth()*0.1f, this.getHeight()*0.1f);

        this.setPosition(shootingPointObject.getPosition().x - this.getWidth()/2, shootingPointObject.getPosition().y);
        this.getBody().setTransform(this.getX(), this.getY(),0);
    }

    public void act(float delta){
        validateOutPosition();
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), true, velocity));
        this.getBody().setTransform(this.getX(), this.getY(), 0);
    }

    public void validateOutPosition(){
        if(this.getY() > GameScreen.HEIGHT + this.getHeight() && !this.isAddedToDispose()){
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
