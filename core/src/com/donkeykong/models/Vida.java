package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vida {
    private Texture vida;
    private Texture vidaPerdida;
    private Sprite imagemAtual;
    private boolean isAlive;
    private int     height      ;
    private int     width       ;
    private int     posicaoX    ;
    private int     posicaoY    ;


    public Vida(int height, int width, int posX, int posY){
        this.vida = new Texture(Gdx.files.internal("personagens/vida/heart.png"));
        this.vidaPerdida = new Texture(Gdx.files.internal("personagens/vida/broken_heart.png"));
        isAlive = true;
        this.height = height;
        this.width = width;
        this.posicaoX = posX;
        this.posicaoY = posY;
        defineSprite();
    }

    public void defineSprite(){
        imagemAtual = new Sprite(this.vida);
        imagemAtual.setSize(width, height);
        imagemAtual.setPosition(posicaoX, posicaoY);
    }

    public void atualizarVida(){
        this.isAlive = false;
        this.imagemAtual = new Sprite(this.vidaPerdida);
        imagemAtual.setSize(width, height);
        imagemAtual.setPosition(posicaoX, posicaoY);
    }

    public void draw(SpriteBatch batch){
        imagemAtual.draw(batch);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
