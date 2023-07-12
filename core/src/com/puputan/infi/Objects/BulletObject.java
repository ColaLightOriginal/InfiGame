package com.puputan.infi.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Utils.CollisionsDetector;
import com.puputan.infi.Utils.MovementUtils;
import lombok.Getter;

@Getter
public class BulletObject extends BaseObject {

    private final Texture texture = AssetsRepository.bulletTexture;

    private Vector2 position;
    private final Sprite sprite;
    private final float velocity = 600;
    private final Rectangle boundingRectangle;

    public BulletObject(ShootingPointObject shootingPointObject){
        this.sprite = new Sprite(texture);
        this.boundingRectangle = this.sprite.getBoundingRectangle();
        this.scaleSize(0.2f, this.getSprite());
        this.position = new Vector2(shootingPointObject.getPosition().x - this.sprite.getWidth()/2, shootingPointObject.getPosition().y);
        this.sprite.setPosition(position.x, position.y);
    }

    @Override
    public void update(){
        this.position.y = MovementUtils.moveVertical(this.position, true, velocity);
        this.sprite.setPosition(position.x, position.y);
        super.draw(this.sprite);
    }

    @Override
    public void onCollisionDetection() {

    }

    public void collisionHandler(Sprite sprite, String tag){
        
    }
}
