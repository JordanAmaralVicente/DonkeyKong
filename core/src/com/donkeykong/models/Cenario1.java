package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Cenario1 {
    private Image imageStage;
    private Texture skin;
    private int posicaoX, posicaoY;
    private int width, height;
    Stage stage;

    public Cenario1(Stage stage, int posicaoX, int posicaoY, int width, int height){
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.width = width;
        this.height = height;
        this.stage = stage;

        skin = new Texture(Gdx.files.internal("cenarios/stage_1.png"));

        imageStage = new Image(skin);
        imageStage.setPosition(this.posicaoX,this.posicaoY);
        imageStage.setSize(this.width, this.height);

        this.stage.addActor(imageStage);
    }

}
