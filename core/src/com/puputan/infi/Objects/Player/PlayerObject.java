package com.puputan.infi.Objects.Player;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.BaseObject;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.ExperiencePointObject;
import com.puputan.infi.Systems.PlayerSystems;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MouseUtils;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerObject extends BaseObject {

    private final float PLAYER_BORDER_HEIGHT_VALUE = 0.5f;
    @Setter
    private float VELOCITY = 200;
    private final float MAX_VELOCITY = 600;
    private final float borderHeightPosition;
    private static final float dashDistance = Gdx.graphics.getWidth()*0.16f;
    private static final float dashResetTimeInSeconds = 1;
    private static float lastDashTime = GameScreen.gameTime;

    private final PlayerSystems playerSystems;

    public PlayerObject() {
        super(AssetsRepository.playerTexture);

        BodyUtils.loader.attachFixture(this.getBody(), "Player", BodyUtils.getDefaultFixture(), this.getWidth());

        this.setPosition(GameScreen.WIDTH/2, GameScreen.HEIGHT*0.1f);
        this.borderHeightPosition = Gdx.graphics.getHeight()*PLAYER_BORDER_HEIGHT_VALUE;

        this.playerSystems = new PlayerSystems(this);
    }

    public void act(float delta){
        positionToMousePosition();
    }

    @Override
    public void onDestroy() {
    }

    public void positionToMousePosition(){
        Vector2 mousePosition = MouseUtils.getMousePositionVector();
        Vector2 targetPosition = validatePlayerMovementPosition(mousePosition);
        Vector2 resultPosition;

        Vector2 actualPosition = new Vector2(this.getX(), this.getY());
        resultPosition = MovementUtils.moveTowardsPoint(actualPosition, targetPosition, this.VELOCITY);
        this.moveObjectPosition(resultPosition);
    }

    public void dash(){
        if(GameScreen.gameTime-lastDashTime >= dashResetTimeInSeconds) {
            Vector2 returnPosition = MovementUtils.dashToPosition(this.getMiddlePosition(),
                    MouseUtils.getMousePositionVector(), dashDistance);
            returnPosition = validatePlayerMovementPosition(returnPosition);
            this.moveObjectPosition(returnPosition);
            lastDashTime = GameScreen.gameTime;
        }
    }

    public Vector2 validatePlayerMovementPosition(Vector2 targetPosition){
        Vector2 resultVector = new Vector2(targetPosition.x-this.getWidth()/2, targetPosition.y-this.getHeight()/2);

        if(targetPosition.y > borderHeightPosition) resultVector.y = borderHeightPosition-this.getHeight()/2;
        if(targetPosition.x < 0) resultVector.x = 0 - this.getWidth()/2;
        if(targetPosition.y < 0) resultVector.y = 0 - this.getHeight()/2;
        if(targetPosition.x > Gdx.graphics.getWidth()) resultVector.x = Gdx.graphics.getWidth() - this.getWidth()/2;
        return resultVector;
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof EnemyObject){
            this.playerSystems.hit();
        }else if(object instanceof ExperiencePointObject){
            this.playerSystems.gainExp();
        }
    }
}
