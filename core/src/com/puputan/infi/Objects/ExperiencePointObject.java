package com.puputan.infi.Objects;


import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;

public class ExperiencePointObject extends BaseObject {

    private float velocity = 75;
    private boolean isAddedToDispose;

    public ExperiencePointObject(Vector2 position) {
        super(AssetsRepository.expTexture);
        GameScreen.stage.addActor(this);

        this.setPosition(position.x, position.y);
        this.getBody().setTransform(this.getY(), this.getY(), 0);
    }

    @Override
    public void act(float delta) {
        validateOutPosition();
        if(MovementUtils.getDistanceBetweenObjects(this, GameScreen.playerObject) < 200000f){
            Vector2 newPosition;
            newPosition = MovementUtils.moveTowardsPoint(new Vector2(this.getX(), this.getY()),
                    new Vector2(GameScreen.playerObject.getX(), GameScreen.playerObject.getY()), this.velocity + 100f);
            this.setPosition(newPosition.x, newPosition.y);
        }
        else this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), false, velocity));
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
