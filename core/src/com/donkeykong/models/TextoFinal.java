package com.donkeykong.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class TextoFinal {
    private final BitmapFont bitmapFont   ;
    String texto;

    public TextoFinal(String texto){
        this.bitmapFont = new BitmapFont();
        this.bitmapFont.getData().setScale(5, 5);
        this.bitmapFont.setColor(Color.PURPLE);

        this.texto = texto;

    }

    public void draw(SpriteBatch batch){
        bitmapFont.draw(batch, "" + texto, 170, 690);
    }

}
