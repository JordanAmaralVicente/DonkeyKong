package com.donkeykong.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.donkeykong.models.SimpleButton;

public class StartGame extends Game {
    SpriteBatch batch;
    SimpleButton btnStart;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture buttonSkin = new Texture(Gdx.files.internal("playImageButton.png"));
        btnStart = new SimpleButton(buttonSkin, 0, 0, 396, 180);
        font = new BitmapFont();

        setScreen(new Tela1(this));

    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
