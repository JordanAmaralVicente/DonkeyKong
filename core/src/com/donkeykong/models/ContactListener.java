package com.donkeykong.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    World world;

    public ContactListener(World world){
        this.world = world;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getUserData() == "escada" || fb.getUserData() == "escada"){
            Mario.activateStair = true;
        }

        System.out.println(Mario.activateStair);


    }

    @Override
    public void endContact(Contact contact) {
        Mario.activateStair = false;
        System.out.println(Mario.activateStair);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
