package com.puputan.infi.Objects;


import Screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.puputan.infi.Configurations.AssetsRepository;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Utils.BodyUtils;
import com.puputan.infi.Utils.MovementUtils;

public class ExperiencePointObject extends BaseObject {

    private Body body;
    private float velocity = 75;
    public ExperiencePointObject(Vector2 position) {
        super(AssetsRepository.expTexture);
        GameScreen.stage.addActor(this);
        body = BodyUtils.defineBody(BodyDef.BodyType.DynamicBody, position);
        this.body.setUserData(this);
        this.setPosition(position.x, position.y);
        this.body.setTransform(position.x, position.y, 0);
        this.setSize(this.getWidth()*0.1f, this.getHeight()*0.1f);
    }

    @Override
    public void act(float delta) {
        this.setY(MovementUtils.moveVertical(new Vector2(this.getX(), this.getY()), false, velocity));
        this.body.setTransform(this.getX(), this.getY(), 0);
    }

    @Override
    public void onCollision(Fixture fixture) {
        Object ob = fixture.getBody().getUserData();
        if (ob instanceof PlayerObject) {
            this.addToDispose(body);
        }
    }
}
