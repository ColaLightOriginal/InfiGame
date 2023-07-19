package com.puputan.infi.Objects.Player;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.*;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MouseUtils;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerObject extends BaseObject {

    private final float PLAYER_BORDER_HEIGHT_VALUE = 0.35f;
    private final float borderHeightPosition;

    private final PlayerFuctions playerFuctions;

    public PlayerObject() {
        super(AssetsRepository.playerTexture);

        this.setPosition(GameScreen.WIDTH/2, GameScreen.HEIGHT*0.1f);
        this.borderHeightPosition = Gdx.graphics.getHeight()*0.25f;
        this.playerFuctions = new PlayerFuctions(this);
    }

    public void act(float delta){
        positionToMousePosition();
        this.getBody().setTransform(this.getX() + this.getImageWidth()/2, this.getY() + + this.getImageHeight()/2, 0);
    }

    public void positionToMousePosition(){
        Vector2 targetPosition = validatePlayerMovementPosition(this);
        Vector2 resultPosition;

        Vector2 actualPosition = new Vector2(this.getX(), this.getY());
        resultPosition = MovementUtils.moveTowardsPoint(actualPosition, targetPosition, 600);
        this.setPosition(resultPosition.x, resultPosition.y);
    }

    public Vector2 validatePlayerMovementPosition(Image image){
        Vector2 mousePosition = new Vector2(MouseUtils.getMousePositionX(), Gdx.graphics.getHeight() - MouseUtils.getMousePositionY());
        Vector2 resultVector = new Vector2(mousePosition.x, mousePosition.y);

        if(mousePosition.y > borderHeightPosition) resultVector.y = borderHeightPosition-image.getHeight()/2;
        if(mousePosition.x < 0) resultVector.x = 0 + image.getWidth()/2;
        if(mousePosition.y < 0) resultVector.y = 0 + image.getHeight()/2;
        if(mousePosition.x > Gdx.graphics.getWidth()) resultVector.x = Gdx.graphics.getWidth() - image.getWidth()/2;
        return resultVector;
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if(object instanceof EnemyObject){
            this.playerFuctions.hit();
        }else if(object instanceof ExperiencePointObject){
            this.playerFuctions.gainExp();
        }
    }
}
