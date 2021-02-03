package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.donkeykong.models.*;

import java.util.ArrayList;
import java.util.List;

public class Tela1 extends ScreenAdapter {
    Stage stage;
    StartGame game;
    Mario mario;
    Macaco donkeyKong;
    Princesa princesa;
    Cenario1 cenario1;
    List<Escada> stairs = new ArrayList<>(); //lista para mapear as posicoes das escadas

    boolean escada;

    public Tela1(StartGame game) {
        this.game = game;
        stage = new Stage(); //palco para os atores (mario e escada por enquanto)

        donkeyKong = new Macaco(10, 490, 50, 50); //DK
        mario = new Mario(stage, 25, 17, 40, 40); //MARIO
        cenario1 = new Cenario1(stage, 0, 0, 500, 500); //CENARIO DE FUNDO

        stairs.add(new Escada(421, 17, 12, 64)); //MAPEEI APENAS A PRIMEIRA ESCADA
    }

    @Override
    public void render(float delta) {
        game.cam.update();

        {//Início das configurações da janela do Jogo
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.graphics.setTitle("Tela 1");
            //Tamanho da janela: 500x550
        }//Fim das configurações de jane

        //Início das verificações de input
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { //direita
            this.donkeyKong.atualizaImagem(true);

            if (Mario.canMove) {
                mario.atualizaImagem(Mario.status);
                mario.mover(Acoes.MOVE_RIGHT, stairs);
                Mario.canMove = false;

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Mario.canMove = true;
                    }
                }, 0.05f); //sem o timer o mario anda mt rapido

            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { //esquerda
            this.donkeyKong.atualizaImagem(false);

            if (Mario.canMove) {
                mario.atualizaImagem(Mario.status);
                mario.mover(Acoes.MOVE_LEFT, stairs);
                Mario.canMove = false;

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Mario.canMove = !Mario.canMove;
                    }
                }, 0.05f);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { //cima
            mario.mover(Acoes.MOVE_UP, stairs);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { //baixo
            mario.mover(Acoes.MOVE_DOWN, stairs);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) { //testes
            mario.mover(Acoes.MOVE_F, stairs);
        }

        {//Início da criação das imagens na tela
            game.batch.begin();
            this.donkeyKong.draw(game.batch);
            game.batch.end();

            stage.act(delta); //desenha todos os atores
            stage.draw();
        }//Fim da criação das imagens na tela

    }//Fim das verificações de input
}

