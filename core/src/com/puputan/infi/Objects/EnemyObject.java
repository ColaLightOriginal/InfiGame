package com.puputan.infi.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.InfiGame;
import com.puputan.infi.Utils.MovementUtils;

public class EnemyObject extends BaseObject {

    private final Texture texture = AssetsRepository.enemyTexture;
    private final Sprite sprite;
    private final Vector2 position;
    private final float velocity = 150;
    private final Rectangle boundingRectangle;

    public EnemyObject(){
        this.sprite = new Sprite(this.texture);
        this.boundingRectangle = this.sprite.getBoundingRectangle();
        scaleSize(0.2f, this.sprite);
        this.position = new Vector2(InfiGame.WIDTH/2-this.sprite.getWidth()/2, InfiGame.HEIGHT);
        this.sprite.setPosition(position.x, position.y);
    }

    @Override
    public void update() {
        this.position.y = MovementUtils.moveVertical(this.position, false, velocity);
        this.sprite.setY(position.y);
        super.draw(this.sprite);
    }

    @Override
    public void onCollisionDetection() {

    }
}
