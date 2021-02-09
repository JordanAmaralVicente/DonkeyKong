package com.donkeykong.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Vida> deckDeVidas;
    private int vidas;
    public Deck(){
        this.vidas = 3;
        this.deckDeVidas = new ArrayList<>();
        this.deckDeVidas.add(new Vida(32, 32, 100, 100));
        this.deckDeVidas.add(new Vida(32, 32, 142, 100));
        this.deckDeVidas.add(new Vida(32, 32, 182, 100));
    }

    public void draw(SpriteBatch batch){
        this.deckDeVidas.forEach(vida -> {
            vida.draw(batch);
        });
    }

    public boolean atualizarVida(){
        if(vidas > 0){
            vidas--;
            deckDeVidas.get(vidas).atualizarVida();
            return true;
        } else{
            deckDeVidas.get(0).atualizarVida();
            return false;
        }
    }

}
