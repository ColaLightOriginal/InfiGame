package com.puputan.infi.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Utils.CollisionsDetector;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

@Getter
public class BulletObject extends BaseObject implements GameObjectInterface{

    private final Sprite sprite;
    private Vector2 position;
    private Vector2 direction;
    private final float velocity = 600;
    private final Rectangle boundingRectangle;

    public BulletObject(Texture texture, ShootingPointObject shootingPointObject){
        this.sprite = new Sprite(texture);
        this.boundingRectangle = this.sprite.getBoundingRectangle();
        this.scaleSize(0.2f, this.getSprite());
        this.position = new Vector2(shootingPointObject.getPosition().x - this.sprite.getWidth()/2, shootingPointObject.getPosition().y);
        this.sprite.setPosition(position.x, position.y);
    }

    @Override
    public void update(SpriteBatch batch){
        this.position.y = MovementUtils.moveVertical(this.position, true, velocity);
        this.sprite.setPosition(position.x, position.y);
        this.sprite.draw(batch);
    }

    public void collisionHandler(Sprite sprite, String tag){
        
    }
}
