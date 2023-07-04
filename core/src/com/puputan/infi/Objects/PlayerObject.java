package com.puputan.infi.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Utils.MouseUtils;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class PlayerObject extends BaseObject implements GameObjectInterface{

    private final float PLAYER_BORDER_HEIGHT_VALUE = 0.25f;

    private final Texture texture;
    private final Sprite sprite;
    private final float borderHeightPosition;
    private final ArrayList<ShootingPointObject> shootingPointObjectsList;
    private final Rectangle boundingRectangle;

    public PlayerObject(float x, float y, Texture texture) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.boundingRectangle = this.sprite.getBoundingRectangle();

        scaleSize(0.5f, this.sprite);
        this.borderHeightPosition = Gdx.graphics.getHeight()*0.25f;

        this.shootingPointObjectsList = new ArrayList<>();
        this.shootingPointObjectsList.add(new ShootingPointObject(this));
    }

    @Override
    public void update(SpriteBatch batch){
        positionToMousePosition();
        sprite.draw(batch);
    }

    public void positionToMousePosition(){
        Vector2 targetPosition = validatePlayerMovementPosition();
        Vector2 resultPosition = new Vector2();
        System.out.println("Target x: " + targetPosition.x);
        System.out.println("Target y: " + targetPosition.y);

        Vector2 actualPosition = new Vector2(this.sprite.getX(), this.sprite.getY());
        resultPosition = MovementUtils.moveTowardsPoint(actualPosition, targetPosition, 600);
        this.sprite.setPosition(resultPosition.x, resultPosition.y);
    }

    public Vector2 validatePlayerMovementPosition(){
        Vector2 mousePosition = new Vector2(MouseUtils.getMousePositionX(), Gdx.graphics.getHeight() - MouseUtils.getMousePositionY());
        Vector2 resultVector = new Vector2(mousePosition.x, mousePosition.y);

        if(mousePosition.y > borderHeightPosition) resultVector.y = borderHeightPosition;
        if(mousePosition.x < 0) resultVector.x = 0 + this.sprite.getWidth()/2;
        if(mousePosition.y < 0) resultVector.y = 0 + this.sprite.getHeight()/2;
        if(mousePosition.x > Gdx.graphics.getWidth()) resultVector.x = Gdx.graphics.getWidth() - this.sprite.getWidth()/2;
        return resultVector;
    }

    public ArrayList<BulletObject> shoot(Texture bulletTexture){
        ArrayList<BulletObject> bulletsList = new ArrayList<>();
        for (ShootingPointObject shootingPoint : this.shootingPointObjectsList) {
            shootingPoint.updatePosition(this);
            bulletsList.add(new BulletObject(bulletTexture, shootingPoint));
        }
        return bulletsList;
    }


}
