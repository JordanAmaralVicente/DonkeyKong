package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Macaco {
    private Texture imageRight  ;
    private Texture imageLeft   ;
    public  Sprite  spriteMacaco;
    private int     height      ;
    private int     width       ;
    private int     posicaoX    ;
    private int     posicaoY    ;


    public void atualizaImagem(boolean direcao) {
        if(direcao){
            this.spriteMacaco.setTexture(this.imageRight);
        }
        if(!direcao){
            this.spriteMacaco.setTexture(this.imageLeft);
        }
    }


    public Macaco(int posX, int posY){
        this.posicaoX = posX;
        this.posicaoY = posY;

    }

    public Macaco(int posX, int posY, int height, int width){
        this.posicaoX     = posX  ;
        this.posicaoY     = posY  ;
        this.height       = height;
        this.width        = width ;
        this.imageLeft    = new Texture(Gdx.files.internal("personagens/donkey/donkey_left.png"));
        this.imageRight   = new Texture(Gdx.files.internal("personagens/donkey/donkey_right.png"));
        this.spriteMacaco = new Sprite(this.imageRight);
        this.spriteMacaco.setPosition(this.posicaoX, this.posicaoY);
        this.spriteMacaco.setSize(this.width, this.height);
    }


    public void draw(SpriteBatch batch){
        this.spriteMacaco.draw(batch);
    }

}
