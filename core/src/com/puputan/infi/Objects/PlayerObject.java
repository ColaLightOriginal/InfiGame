package com.puputan.infi.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MouseUtils;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;

@Getter
public class PlayerObject extends BaseObject {

    private final float PLAYER_BORDER_HEIGHT_VALUE = 0.35f;
    private final float borderHeightPosition;
    private final String tag = "Player";
    private final ArrayList<ShootingPointObject> shootingPointObjectsList;
    private final Body body;

    public PlayerObject() {
        super(AssetsRepository.playerTexture);
        InfiGame.stage.addActor(this);
        this.setSize(this.getWidth()*0.25f, this.getHeight()*0.25f);

        this.body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody,
                new Vector2(this.getX() , this.getY()+this.getImageHeight()/2));
        this.body.setUserData(this);

        this.borderHeightPosition = Gdx.graphics.getHeight()*0.25f;
        this.shootingPointObjectsList = new ArrayList<>();
        this.shootingPointObjectsList.add(new ShootingPointObject(this));
    }

    public void act(float delta){
        positionToMousePosition();
        this.body.setTransform(this.getX() + this.getImageWidth()/2, this.getY() + + this.getImageHeight()/2, 0);
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

    public ArrayList<BulletObject> shoot(Texture bulletTexture){
        ArrayList<BulletObject> bulletsList = new ArrayList<>();
        for (ShootingPointObject shootingPoint : this.shootingPointObjectsList) {
            shootingPoint.updatePosition(this);
            bulletsList.add(new BulletObject(shootingPoint));
        }
        return bulletsList;
    }

    @Override
    public void onCollision(Fixture fixture) {
        System.out.println("Player collided");
    }
}
