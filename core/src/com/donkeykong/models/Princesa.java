package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Princesa {
    public Sprite spritePrincesa;

    public Princesa(int posX, int posY, int height, int width){
        this.spritePrincesa = new Sprite(new Texture(Gdx.files.internal("personagens/princesa/princesa.png")));
        this.spritePrincesa.setSize(width, height);
        this.spritePrincesa.setPosition(posX, posY);
    }

    public void draw(SpriteBatch batch) {
        this.spritePrincesa.draw(batch);
    }

}
