package com.donkeykong.models;

import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.Tela1;

import java.util.ArrayList;
import java.util.List;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    World world;
    Deck deckDeVidas;
    Vida vida;
    Martelo martelo;
    Mario mario;

    public ContactListener(World world, Deck deckDeVidas, Martelo martelo, Mario mario) {
        this.world = world;
        this.deckDeVidas = deckDeVidas;
        this.martelo = martelo;
        this.mario = mario;
    }

    //CHAO == 2
    //ESCADA == 4

    @Override
    public void beginContact(Contact contact) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;


//        System.out.println(firstBit);
//        System.out.println(secondBit);


        //mario e a escada
        if (firstBit == 4 || secondBit == 4) {
            if (firstBit == 1 || secondBit == 1) {
                Mario.estouNaEscada = true;
                System.out.println("Cheguei na escada");
            }
        }

        if (firstBit == 500 || secondBit == 500) {
            if (firstBit == 1 || secondBit == 1) {
                System.out.println("Fim do jogo");
                Tela1.fimDoJogo = true;
            }
        }

        //mario e o free fire
        if (firstBit == 64 || secondBit == 64) {
            if (firstBit == 1 || secondBit == 1) {
                deckDeVidas.atualizarVida();

                if(deckDeVidas.getVidas() <= 0)
                    Tela1.jogoPerdido = true;
//                System.out.println("Leroi");

                if (firstBit == 64)
//                    contact.getFixtureA().getBody().setUserData("destruir");
                    mario.verificaColisao(contact.getFixtureA().getBody());
                else
                    mario.verificaColisao(contact.getFixtureB().getBody());

//                    contact.getFixtureB().getBody().setUserData("destruir");
            }
        }

        //mario e a poppy
        if (firstBit == 32 || secondBit == 32) {
            if (firstBit == 1 || secondBit == 1) {
                System.out.println("AI PAPEI PEGUEI UM MARTELO");
                Martelo.autoDestruir = true;
                Mario.estaComOMartelo = true;

            }
        }

        //mario e o chão
        if (firstBit == 2 || secondBit == 2) {
            if (firstBit == 1 || secondBit == 1) {
                Mario.estouNaEscada = false;
                Mario.estouNoChao = true;
                System.out.println("Cai no chão");
            }
        }

        if (firstBit == 512 || secondBit == 512) {
            if (firstBit == 1 || secondBit == 1) {
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

//        if ((firstBit | secondBit) == (64 | 2 | 4)) {
//            System.out.println("Contact " + firstBit + " " + secondBit);
//            contact.setEnabled(false);
//        }

        if (firstBit == secondBit)
            contact.setEnabled(false);

        if (firstBit == 64 || secondBit == 64) {
            if (firstBit == 1 || secondBit == 1) {
                contact.setEnabled(false);
            }
        }

        if (firstBit == 64 || secondBit == 64) {
            if (firstBit == 256 || secondBit == 256) {
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
