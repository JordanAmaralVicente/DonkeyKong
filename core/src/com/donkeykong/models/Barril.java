package com.donkeykong.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Shape2D;

public class Barril implements  Posicao{
    private Texture imagemBarril;
    private Shape2D hitBox      ;
    private int     raio        ;
    private int     posicaoX    ;
    private int     posicaoY    ;

    public void girar(){
        //Todo to implements
    }

    public void cairOuNaoDaEscada(){
        //Todo implements
    }

    public boolean tomeiMarretada(){
        //Todo implements
        return false;
    }

    @Override
    public void atualizaPosicao() {

    }
}
