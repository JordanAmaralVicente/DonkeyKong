package com.donkeykong.models;

import com.badlogic.gdx.physics.box2d.*;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    World world;

    public ContactListener(World world) {
        this.world = world;
    }

    //CHAO == 2
    //ESCADA == 4

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getFilterData().categoryBits == 4 || fb.getFilterData().categoryBits == 4)
            Mario.estouNaEscada = true;

        if(fa.getFilterData().categoryBits == 2 || fb.getFilterData().categoryBits == 2){
            Mario.estouNaEscada = false;
            Mario.estouNoChao = true;
        }

        System.out.println(Mario.estouNaEscada);

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getFilterData().categoryBits == 8 || fb.getFilterData().categoryBits == 8)
            System.out.println("Termina aqui!");

        System.out.println(Mario.estouNaEscada);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
