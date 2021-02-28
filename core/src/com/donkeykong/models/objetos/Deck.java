package com.donkeykong.models.objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.donkeykong.models.utilitarios.Vida;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Vida> deckDeVidas;
    private int vidas;
    public boolean vivo;
    public Deck(){
        this.vidas = 3;
        this.vivo = true;
        this.deckDeVidas = new ArrayList<>();
        this.deckDeVidas.add(new Vida(48, 48, 550, 660));
        this.deckDeVidas.add(new Vida(48, 48, 592, 660));
        this.deckDeVidas.add(new Vida(48, 48, 634, 660));
    }

    public void draw(SpriteBatch batch){
        this.deckDeVidas.forEach(vida -> vida.draw(batch));
    }

    public int getVidas() {
        return vidas;
    }

    public void atualizarVida(){
        if(vidas > 0){
            vidas--;
            deckDeVidas.get(vidas).atualizarVida();
            if(vidas == 0){
                this.vivo = false;
            }
        } else {
            deckDeVidas.get(0).atualizarVida();
            this.vivo = false;
        }
    }

    public boolean isAlive(){
        return this.vivo;
    }

}
