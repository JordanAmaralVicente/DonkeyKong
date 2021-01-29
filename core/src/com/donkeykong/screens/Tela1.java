package com.donkeykong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.donkeykong.models.Macaco;
import com.donkeykong.models.Mario;
import com.donkeykong.models.Princesa;

public class Tela1 extends ScreenAdapter {
    StartGame game;
    Mario mario;
    Macaco donkeyKong;
    Princesa princesa;

    public Tela1(StartGame game) {
        this.game = game;

        this.donkeyKong = new Macaco(10, 490,50, 50);

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

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            System.out.println("X" + Gdx.input.getX() + "   Y " + Gdx.input.getY());
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        }

    }

    @Override
    public void render(float delta) {
        game.cam.update();

        {//Início das configurações da janela do Jogo
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.graphics.setTitle("Tela 1");
            //Tamanho da janela: 640x480

        }//Fim das configurações de jane

        {//Início da criação das imagens na tela
            game.batch.begin();
            //game.btnStart.update(game.batch, 0, 0);
            //game.ponteSpriteE.draw(game.batch);
            //game.ponteSpriteW.draw(game.batch);
            this.donkeyKong.draw(game.batch);
            game.stageSprite.draw(game.batch);
            game.batch.end();
        }//Fim da criação das imagens na tela

        //Início das verificações de input


        if (Gdx.input.isTouched()) { // Verifica se o Botão foi clicado através do mouse
            System.out.println("Input X: " + Gdx.input.getX() + "\n Input Y: " + Gdx.input.getY());
           /*
            if(game.btnStart.checkIfClicked(Gdx.input.getX(), Gdx.input.getY())){
                game.setScreen(new Tela2(game));
            }*/
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.donkeyKong.atualizaImagem(true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.donkeyKong.atualizaImagem(false);
        }
    }//Fim das verificações de input

}

