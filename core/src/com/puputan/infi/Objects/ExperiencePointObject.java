package com.puputan.infi.Objects;


import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.MovementUtils;

public class ExperiencePointObject extends BaseObject {

    private float velocity = 75;
    private float movingToPlayerVelocity = 200 + velocity;
    private float movingToPlayerDistanceThreshold = 0.2f;
    private boolean isAddedToDispose;
    private boolean isMovingToPlayer;

    public ExperiencePointObject(Vector2 position) {
        super(AssetsRepository.expTexture);
        GameScreen.gameStage.addActor(this);

        this.setPosition(position.x, position.y);
        this.getBody().setTransform(this.getY(), this.getY(), 0);

        this.isMovingToPlayer = false;
    }

    @Override
    public void act(float delta) {
        validateOutPosition();
        if(MovementUtils.getDistanceBetweenObjects(this, GameScreen.playerObject) < movingToPlayerDistanceThreshold*GameScreen.HEIGHT || this.isMovingToPlayer){
            this.isMovingToPlayer = true;
            Vector2 newPosition;
            newPosition = MovementUtils.moveTowardsPoint(this.getVectorPosition(), GameScreen.playerObject.getMiddlePosition(), movingToPlayerVelocity);
            this.setPosition(newPosition.x, newPosition.y);
        }
        else this.setY(MovementUtils.moveVertical(this.getVectorPosition(), false, velocity));
        this.getBody().setTransform(this.getX(), this.getY(), 0);
    }

    public void validateOutPosition(){
        if(this.getY() > GameScreen.HEIGHT + this.getHeight()){
            this.addToDispose(this.getBody());
        }
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object ob = fixture.getBody().getUserData();
        if (ob instanceof PlayerObject && !isAddedToDispose) {
            this.isAddedToDispose = true;
            this.addToDispose(getBody());
        }
    }
}
