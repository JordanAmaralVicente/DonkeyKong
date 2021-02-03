package com.donkeykong.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

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

    }
}
