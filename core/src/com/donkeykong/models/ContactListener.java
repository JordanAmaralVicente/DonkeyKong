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
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

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
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getFilterData().categoryBits == 8 || fb.getFilterData().categoryBits == 8)
            System.out.println("Termina aqui!");

        System.out.println(Mario.estouNaEscada);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

        System.out.println(firstBit);
        System.out.println(secondBit);

        if ((firstBit | secondBit) == (64 | 2 | 4)) {
            System.out.println("Contact " + firstBit + " " + secondBit);
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
