package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.donkeykong.models.*;

import java.util.ArrayList;

public class Tela1 extends ScreenAdapter {
    //Box2D variaveis
    World mundo;
    Box2DDebugRenderer b2dr;
    IniciarMundo iniciarMundo;

    //Tiledmap variables
    TiledMapRenderer renderizadorMapa;

    //Game models variables
    StartGame game;
    Mario mario;
    Macaco donkeyKong;
    Deck deckDeVidas;
    Pontuacao pontos;

    public Tela1(StartGame game) {
        this.game = game; //instancia do inicio do jogo (a camera está lá e a batch também)

        //Iniciando o mundo e coletando as variaveis que serão utilizadas
        iniciarMundo = new IniciarMundo();
        mundo = iniciarMundo.getMundo();
        b2dr = iniciarMundo.getRenderizadorBox2D();
        renderizadorMapa = iniciarMundo.getRenderizadorMapa();

        deckDeVidas = new Deck();
        pontos = new Pontuacao();
        mario = new Mario(20,70, mundo);

        donkeyKong = new Macaco(300, 495, 100, 100); //DK
        renderizadorMapa.setView(game.cam);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mundo.step(1 / 60f, 6, 2);
        renderizadorMapa.render();
        handleInput(delta);
        mario.update(delta);
        b2dr.render(mundo, game.cam.combined);
        game.cam.update();

        {//Início da criação das imagens na tela
            game.batch.begin();
            donkeyKong.draw(game.batch, delta);
            deckDeVidas.draw(game.batch);
            game.batch.draw(mario, mario.getX() - mario.getWidth() / 2f,
                    mario.getY() - mario.getHeight() / 2f);
            pontos.draw(game.batch);
            game.batch.end();
        }//Fim da criação das imagens na tela

        if(!pontos.atualizaPontosPeloTempo()){
            this.dispose();
        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            mario.mover(dt, Input.Keys.UP);

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mario.mover(dt, Input.Keys.DOWN);


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mario.mover(dt, Input.Keys.RIGHT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            mario.mover(dt, Input.Keys.LEFT);
            pontos.atualizarPontuacao(99);
        }


        //TESTE DO FUNCIONAMENTO DA PERCA DE VIDA SEM SER NO MÁRIO
        if(Gdx.input.isKeyPressed(Input.Keys.X)){
            if(!deckDeVidas.atualizarVida()){
                //todo como o render atualiza muitas vezes por segundo, ele perde todas vidas de uma vez só
                //por isso, quem for fazer a parada dos fogos, verificar quando o mario está sendo tocado para atualizar vida
            }
        }

    }

}

