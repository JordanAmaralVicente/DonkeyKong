package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.donkeykong.models.*;

import java.util.LinkedList;

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

    //Variaveis de controle do tempo
    float deltaSpawn = 2f;
    float tempoSpawn = 0f;

    int aux = 1;

    Sound somVitoria;

    private boolean ganhouOJogo = false, perdeuOJogo = false;

    public Tela1(StartGame game) {
        this.game = game; //instancia do inicio do jogo (a camera está lá e a batch também)
        somVitoria = Gdx.audio.newSound(Gdx.files.internal("sons/victory.wav"));

        //inicia tudo do mundo e coletando as variaveis de controle necessárias
        iniciarMundo = new IniciarMundo();
        mundo = iniciarMundo.getMundo();
        b2dr = iniciarMundo.getRenderizadorBox2D();
        renderizadorMapa = iniciarMundo.getRenderizadorMapa();

        //Componentes do jogo
        deckDeVidas = new Deck();
        pontos = new Pontuacao();
        donkeyKong = new Macaco(300, 495, 100, 100);
        fogoList = new LinkedList<>();
        mario = new Mario(350, 30, mundo);
        guardaChuva = new GuardaChuva(200, 300, mundo);
        princesa = new Princesa(270, 520, 42, 42, mundo);
        martelo = new Martelo(120, 310, mundo);


        mundo.setContactListener(new GerenciadorDeContato(mundo, deckDeVidas, martelo, mario, this)); //inicia a verificação das colisões
        renderizadorMapa.setView(game.cam);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //limpa a tela

        mundo.step(1 / 60f, 6, 2); //passagem de fps do mundo
        spawnEnemy(delta); //começa os fogos
        renderizadorMapa.render(); //renderiza o mapa
        verificaTeclado(); //verifica as entradas do teclado
        mario.update(delta); //atualiza o mario
        martelo.update(); //atualiza o martelo
//        b2dr.render(mundo, game.cam.combined);
        game.cam.update(); //atualiza a camera

        {//Início da criação das imagens na tela
            game.batch.begin();
            for (Inimigo fogo : fogoList) {
                if (fogo.isVisible()) { //verifica se o fogo nõa foi destruido
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

        if(ganhouOJogo){ //se a princesa tiver sido salva
            somVitoria.play();
            game.setScreen(new TelaVitoria(game));
        }

        if(perdeuOJogo){ //se não restarem vidas
            game.setScreen(new TelaDerrota(game));
        }
    }

    private void spawnEnemy(float delta) {
        tempoSpawn += delta;
        if (tempoSpawn >= deltaSpawn) {
            switch (aux) {
                case 1:
                    fogoList.add(new Inimigo(mundo, 20, 160, 2f, 0));
                    fogoList.add(new Inimigo(mundo, 650, 160, -2f, 0));
                    fogoList.add(new Inimigo(mundo, 325, 160, 2f, 0));
                    tempoSpawn -= deltaSpawn;
                    break;

                case 2:
                    fogoList.add(new Inimigo(mundo, 90, 285, 2.5f, 0));
                    fogoList.add(new Inimigo(mundo, 625, 285, -2.5f, 0));
                    fogoList.add(new Inimigo(mundo, 310, 285, -2.5f, 0));
                    tempoSpawn -= deltaSpawn;
                    break;

                case 3:
                    fogoList.add(new Inimigo(mundo, 90, 410, 3f, 0));
                    fogoList.add(new Inimigo(mundo, 600, 410, -3f, 0));
                    fogoList.add(new Inimigo(mundo, 300, 410, 3f, 0));
                    tempoSpawn -= deltaSpawn;
            }
            aux++;
        }
    }

    public void verificaTeclado() {
        if (mario.corpo.getLinearVelocity().y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                mario.mover(Input.Keys.RIGHT);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mario.mover(Input.Keys.LEFT);
                pontos.atualizarPontuacao(99);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            mario.mover(Input.Keys.UP);

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mario.mover(Input.Keys.DOWN);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            mario.mover(Input.Keys.SPACE);

    }

    public void setGanhouOJogo(boolean ganhouOJogo) {
        this.ganhouOJogo = ganhouOJogo;
    }

    public void setPerdeuOJogo(boolean perdeuOJogo) {
        this.perdeuOJogo = perdeuOJogo;
    }
}

