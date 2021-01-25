package com.donkeykong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class Tela1 extends ScreenAdapter {
    StartGame game;

    public Tela1(StartGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new Tela2(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        {//Início das configurações da janela do Jogo
            Gdx.gl.glClearColor(0, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.graphics.setTitle("Tela 1");
            //Tamanho da janela: 640x480

        }//Fim das configurações de jane

        {//Início da criação das imagens na tela
            game.batch.begin();
            game.btnStart.update(game.batch, 0, 0);
            game.batch.end();
        }//Fim da criação das imagens na tela

        //Início das verificações de input

        if (Gdx.input.isTouched()) { // Verifica se o Botão foi clicado através do mouse
            System.out.println("Input X: " + Gdx.input.getX() + "\n Input Y: " + Gdx.input.getY());
            if(game.btnStart.checkIfClicked(Gdx.input.getX(), Gdx.input.getY())){
                game.setScreen(new Tela2(game));
            }
        }
    }//Fim das verificações de input

}

