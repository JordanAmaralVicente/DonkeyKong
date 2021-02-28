package com.donkeykong.models.utilitarios;

import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.models.objetos.Pontuacao;
import com.donkeykong.visao.Tela1;
import com.donkeykong.models.BitsDeColisao;
import com.donkeykong.models.objetos.Martelo;
import com.donkeykong.models.personagens.Mario;
import com.donkeykong.models.objetos.Deck;

public class GerenciadorDeContato implements ContactListener {
    //Componentes para enviar sinais das colisões
    World mundo;
    Deck deckDeVidas;
    Pontuacao pontos;
    Martelo martelo;
    Mario mario;
    Tela1 tela1;

    public GerenciadorDeContato(World mundo, Deck deckDeVidas, Pontuacao pontos,
                                Martelo martelo, Mario mario, Tela1 tela1) {
        this.mundo = mundo;
        this.deckDeVidas = deckDeVidas;
        this.martelo = martelo;
        this.mario = mario;
        this.tela1 = tela1;
        this.pontos = pontos;
    }

    @Override
    public void beginContact(Contact contact) {
        //todo contato do box2D ocorre entre dois bits
        short primeiroBit = contact.getFixtureA().getFilterData().categoryBits;
        short segundoBit = contact.getFixtureB().getFilterData().categoryBits;

        /*
        Temos sempre que verificar duas vezes, pois não é possível saber
        se o primeiroBit é o mario ou se o segundoBit é o Mario,
        por exemplo.
         */

        //Colisão entre mario e escada
        if (primeiroBit == BitsDeColisao.ESCADA || segundoBit == BitsDeColisao.ESCADA) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {
                mario.setEstouNaEscada(true);
                System.out.println("Cheguei na escada");
            }
        }

        //mario e os inimigos
        if (primeiroBit == BitsDeColisao.FOGO || segundoBit == BitsDeColisao.FOGO) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {

                if (deckDeVidas.getVidas() == 1) //se não restam mais vida
                    tela1.dispose();
                    tela1.setPerdeuOJogo(true);

                //é preciso saber qual dos dois bits é o fogo, para enviar o correto para a verificação de colisão do mario
                if (primeiroBit == BitsDeColisao.FOGO) {
                    if(mario.verificaColisao(contact.getFixtureA().getBody())){
                        pontos.atualizarPontuacao(1);
                    }
                }else
                    mario.verificaColisao(contact.getFixtureB().getBody());
            }
        }

        //mario e o martelo
        if (primeiroBit == BitsDeColisao.MARTELO || segundoBit == BitsDeColisao.MARTELO) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {
                martelo.setAutoDestruir(true); //destroe o martelo para limpar a tela
                mario.setEstaComOMartelo(true);
            }
        }

        //mario e o chão
        if (primeiroBit == BitsDeColisao.CHAO || segundoBit == BitsDeColisao.CHAO) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {
                //mario está em contato com o chão, logo não pode usar o botão de escalar
                mario.setEstouNaEscada(false);
                mario.setEstouNoChao(true);
                System.out.println("Cai no chão");
            }
        }

        //mario e o painel
        //o painel é uma superficie que está em todo o background e impede o mario de sair voando
        if (primeiroBit == BitsDeColisao.PAINEL || segundoBit == BitsDeColisao.PAINEL) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {
                //considera-se que ele está no chão, para não escalar
                mario.setEstouNaEscada(false);
                mario.setEstouNoChao(true);
                System.out.println("Cai no chão");
            }
        }

        if (primeiroBit == BitsDeColisao.PRINCESA || segundoBit == BitsDeColisao.PRINCESA) {
            if (primeiroBit == BitsDeColisao.MARIO || segundoBit == BitsDeColisao.MARIO) {
                System.out.println("Fim do jogo");
                tela1.setGanhouOJogo(true); //a princesa foi salva
            }
        }

    }

    @Override
    public void endContact(Contact contact) { }

    //essa função é chamada antes do contato começar e serve para evitar que dois corpos colidam, quando isso é necessário
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;

        if (firstBit == secondBit) //evita colisão entre os inimigos
            contact.setEnabled(false);

        //evita colisão entre o mario e os inimigos
        if (firstBit == BitsDeColisao.FOGO || secondBit == BitsDeColisao.FOGO) {
            if (firstBit == BitsDeColisao.MARIO || secondBit == BitsDeColisao.MARIO) {
                contact.setEnabled(false);
            }
        }

        //evita colisão entre fogo e parede
        if (firstBit == BitsDeColisao.FOGO || secondBit == BitsDeColisao.FOGO) {
            if (firstBit == BitsDeColisao.PAREDE_INVISIVEL || secondBit == BitsDeColisao.PAREDE_INVISIVEL) {
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { }
}
