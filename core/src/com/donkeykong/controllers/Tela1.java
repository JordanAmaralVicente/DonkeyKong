package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.donkeykong.models.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

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
    LinkedList<Inimigo> fogoList;
    Macaco donkeyKong;
    Martelo martelo;
    GuardaChuva guardaChuva;
    Deck deckDeVidas;
    Pontuacao pontos;
    Princesa princesa;

    //Timing variables
    float timeBetweenEnemySpawn = 2f;
    float spawnTimer = 0;

    int aux = 1;

    Sound somVitoria;


    public static boolean fimDoJogo = false;
    public static boolean jogoPerdido = false;

    public Tela1(StartGame game) {
        this.game = game; //instancia do inicio do jogo (a camera está lá e a batch também)
        somVitoria = Gdx.audio.newSound(Gdx.files.internal("sons/victory.wav"));


        deckDeVidas = new Deck();
        pontos = new Pontuacao();
        donkeyKong = new Macaco(300, 495, 100, 100); //DK

        iniciarMundo = new IniciarMundo();
        mundo = iniciarMundo.getMundo();
        mario = new Mario(350, 30, mundo);


        //Iniciando o mundo e coletando as variaveis que serão utilizadas
        martelo = new Martelo(120, 310, mundo);
        mundo.setContactListener(new ContactListener(mundo, deckDeVidas, martelo, mario));

//        iniciarMundo.setMartelo(martelo);
//        iniciarMundo.setDeckDeVidas(deckDeVidas);

        b2dr = iniciarMundo.getRenderizadorBox2D();
        renderizadorMapa = iniciarMundo.getRenderizadorMapa();

        fogoList = new LinkedList<>();
        guardaChuva = new GuardaChuva(200, 300, mundo);
        princesa = new Princesa(270, 520, 42, 42, mundo);

        renderizadorMapa.setView(game.cam);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(fimDoJogo){
            somVitoria.play();
            game.setScreen(new Tela2(game));
        }

        if(jogoPerdido){
            game.setScreen(new Tela3(game));
        }

        spawnEnemy(delta);
        mundo.step(1 / 60f, 6, 2);
        renderizadorMapa.render();
        handleInput(delta);
        mario.update(delta);
        martelo.update();

        b2dr.render(mundo, game.cam.combined);
        game.cam.update();

        {//Início da criação das imagens na tela
            game.batch.begin();
            for (Inimigo fogo : fogoList) {
//                //System.out.println("Entrei no while");
                if (fogo.visible) {
                    fogo.update(delta);
                    game.batch.draw(fogo, fogo.getX() - fogo.getWidth() / 2f,
                            fogo.getY() - fogo.getHeight() / 2f);
                }


            }
            donkeyKong.draw(game.batch, delta);
            princesa.draw(game.batch);
            deckDeVidas.draw(game.batch);
            game.batch.draw(mario, mario.getX() - mario.getWidth() / 2f,
                    mario.getY() - mario.getHeight() / 2f);
            if (martelo.isVisible())
                game.batch.draw(martelo, martelo.getX() - martelo.getWidth() / 2f,
                        martelo.getY() - martelo.getHeight() / 2f);
            game.batch.draw(guardaChuva, guardaChuva.getX() - guardaChuva.getWidth() / 2f,
                    guardaChuva.getY() - guardaChuva.getHeight() / 2f);
            game.batch.end();
        }//Fim da criação das imagens na tela

        if (!pontos.atualizaPontosPeloTempo()) {
            this.dispose();
        }
    }

    private void spawnEnemy(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= timeBetweenEnemySpawn) {
            switch (aux) {
                case 1:
                    fogoList.add(new Inimigo(mundo, 20, 160, 2f, 0));
                    fogoList.add(new Inimigo(mundo, 650, 160, -2f, 0));
                    fogoList.add(new Inimigo(mundo, 325, 160, 2f, 0));
                    spawnTimer -= timeBetweenEnemySpawn;
                    break;

                case 2:
                    fogoList.add(new Inimigo(mundo, 90, 285, 2.5f, 0));
                    fogoList.add(new Inimigo(mundo, 625, 285, -2.5f, 0));
                    fogoList.add(new Inimigo(mundo, 310, 285, -2.5f, 0));
                    spawnTimer -= timeBetweenEnemySpawn;
                    break;

                case 3:
                    fogoList.add(new Inimigo(mundo, 90, 410, 3f, 0));
                    fogoList.add(new Inimigo(mundo, 600, 410, -3f, 0));
                    fogoList.add(new Inimigo(mundo, 300, 410, 3f, 0));
                    spawnTimer -= timeBetweenEnemySpawn;
            }
            aux++;
        }
    }

    public void handleInput(float dt) {
        if (mario.corpo.getLinearVelocity().y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                mario.mover(dt, Input.Keys.RIGHT);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mario.mover(dt, Input.Keys.LEFT);
                pontos.atualizarPontuacao(99);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            mario.mover(dt, Input.Keys.UP);

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mario.mover(dt, Input.Keys.DOWN);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            mario.mover(dt, Input.Keys.SPACE);

        //TESTE DO FUNCIONAMENTO DA PERCA DE VIDA SEM SER NO MÁRIO
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            if (!deckDeVidas.atualizarVida()) {
                //todo como o render atualiza muitas vezes por segundo, ele perde todas vidas de uma vez só
                //por isso, quem for fazer a parada dos fogos, verificar quando o mario está sendo tocado para atualizar vida
            }
        }
    }
}

