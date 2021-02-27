package com.donkeykong.models.utilitarios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Vida extends Sprite{
    private final Texture vida;
    private final Texture vidaPerdida;
    private final int height;
    private final int width;
    private final int posicaoX;
    private final int posicaoY;


    public Vida(int height, int width, int posX, int posY) {
        super(new Texture(Gdx.files.internal("personagens/vida/heart.png")), width, height);
        this.vida = new Texture(Gdx.files.internal("personagens/vida/heart.png"));
        this.vidaPerdida = new Texture(Gdx.files.internal("personagens/vida/broken_heart.png"));

        this.height = height;
        this.width = width;
        this.posicaoX = posX;
        this.posicaoY = posY;
        
        defineSprite();
    }

    public void defineSprite() {
        setTexture(this.vida);
        setSize(width, height);
        setPosition(posicaoX, posicaoY);
    }

    public void atualizarVida() {
        setTexture(this.vidaPerdida);
        setSize(width, height);
        setPosition(posicaoX, posicaoY);
    }

}
