package com.donkeykong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.donkeykong.screens.StartGame;

public class Tela2 extends ScreenAdapter {
    StartGame game;

    public Tela2(StartGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(10, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Tela 2");

        game.batch.begin();
            game.font.draw(game.batch, "Leroy", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
            game.font.draw(game.batch, "Leroi.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
            game.font.draw(game.batch, "Lerooi", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }
}
