package com.donkeykong.models;

import com.badlogic.gdx.physics.box2d.*;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    World world;
    Deck deckDeVidas;
    Vida vida;
    private final List<TransformacaoMario> observadores = new ArrayList<>();

    public ContactListener(World world) {
        this.world = world;
    }

    //CHAO == 2
    //ESCADA == 4

    @Override
    public void beginContact(Contact contact) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

//        System.out.println(firstBit);
//        System.out.println(secondBit);

        if(firstBit == 4 || secondBit == 4){
            if(firstBit == 1 || secondBit == 1){
                Mario.estouNaEscada = true;
                System.out.println("Cheguei na escada");
            }
        }

        //mario e o free fire
        if(firstBit == 64 || secondBit == 64){
            if(firstBit == 1 || secondBit == 1){
                deckDeVidas.atualizarVida();
                System.out.println("Leroi");
            }
        }

        //mario e a poppy
        if(firstBit == 32 || secondBit == 32){
            if(firstBit == 1 || secondBit == 1){
                System.out.println("AI PAPEI PEGUEI UM MARTELO");
            }
        }

        //mario e o chão
        if(firstBit == 2 || secondBit == 2){
            if(firstBit == 1 || secondBit == 1){
                Mario.estouNaEscada = false;
                Mario.estouNoChao = true;
                System.out.println("Cai no chão");
            }
        }


//        System.out.println(Mario.estouNaEscada);

    }

    @Override
    public void endContact(Contact contact) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

//        if(fa.getFilterData().categoryBits == 8 || fb.getFilterData().categoryBits == 8)
//            System.out.println("Termina aqui!");


//        System.out.println(Mario.estouNaEscada);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

//        System.out.println(firstBit);
//        System.out.println(secondBit);

        if ((firstBit | secondBit) == (64 | 2 | 4)) {
            System.out.println("Contact " + firstBit + " " + secondBit);
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
