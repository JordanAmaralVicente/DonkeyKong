package com.donkeykong.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartGame extends Game {
    SpriteBatch batch;
    OrthographicCamera cam;
    
    public final static float CONVERSAO_METRO_PIXEL = 40f; //conversão de metro (box2d) para pixel (o resto do libgdx)

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / StartGame.CONVERSAO_METRO_PIXEL, Gdx.graphics.getHeight() / StartGame.CONVERSAO_METRO_PIXEL);
        batch = new SpriteBatch();

        setScreen(new Tela1(this));
    }

    @Override
    public void dispose() {
        batch.dispose();

        System.exit(0);
    }
}
