package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.donkeykong.models.IniciarTelaFinal;
import com.donkeykong.models.TextoFinal;

public class Tela2 extends ScreenAdapter {
    StartGame game;
    IniciarTelaFinal iniciarTelaFinal;
    TiledMapRenderer renderizadorMapa;
    TextoFinal gameOver;


    public Tela2(StartGame game) {
        this.game = game;
        gameOver = new TextoFinal("Você ganhou");
        iniciarTelaFinal = new IniciarTelaFinal();
        renderizadorMapa = iniciarTelaFinal.getRenderizadorMapa();

        renderizadorMapa.setView(game.cam);
        game.cam.update();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderizadorMapa.render();
        game.cam.update();

        game.batch.begin();
        gameOver.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
