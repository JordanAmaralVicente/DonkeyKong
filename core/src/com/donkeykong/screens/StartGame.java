package com.donkeykong.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.donkeykong.models.SimpleButton;

public class StartGame extends Game {
    SpriteBatch batch;
    SimpleButton btnStart;
    BitmapFont font;
    OrthographicCamera cam;
    Texture ponteE, ponteW, stage;
    Sprite ponteSpriteE, ponteSpriteW, stageSprite;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        Texture buttonSkin = new Texture(Gdx.files.internal("playImageButton.png"));
        //Texture ponte = new Texture(Gdx.files.internal("Walkway 2 W.png"));

        //btnStart = new SimpleButton(buttonSkin, 0, 0, 396, 180);
        //ponteSprite = new Sprite(ponte, 0, 0, 64,64);
        //ponteSprite.setPosition(500,1000);
        font = new BitmapFont();

        //ponteE = new Texture(Gdx.files.internal("Walkway 2 E.png"));
        //ponteW = new Texture(Gdx.files.internal("Walkway 2 W.png"));
        stage = new Texture(Gdx.files.internal("cenarios/stage_1.png"));

        //ponteSpriteE = new Sprite(ponteE, 0, 0, 64, 64);
        //ponteSpriteW = new Sprite(ponteW, 0, 0, 128, 128);
        stageSprite = new Sprite(stage, 0, 0, 194, 166);

        //ponteSprite.setPosition(500,1000);
        //ponteSpriteE.setSize(64,64);
        //ponteSpriteW.setSize(64,64);
        stageSprite.setSize(500,500);

        //ponteSpriteE.setPosition(0,0);
        //ponteSpriteW.setPosition(200,200);
        stageSprite.setPosition(0,0);

        setScreen(new Tela1(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
