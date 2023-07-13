package com.puputan.infi.Listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.puputan.infi.Objects.BaseObject;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        BaseObject boA = (BaseObject) fixtureA.getBody().getUserData();
        boA.onCollision(fixtureA);
        BaseObject boB = (BaseObject) fixtureB.getBody().getUserData();
        boB.onCollision(fixtureB);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
