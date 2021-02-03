package com.donkeykong.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartGame extends Game {
    SpriteBatch batch;
    OrthographicCamera cam;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        setScreen(new Tela1(this));
    }

    @Override
    public void dispose() {
        batch.dispose();

        System.exit(0);
    }
}
