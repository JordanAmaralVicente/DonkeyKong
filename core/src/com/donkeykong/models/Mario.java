package com.donkeykong.models;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Shape2D;

public class Mario implements Posicao, Imagem{
    private Texture imagemMario       ;
    private Shape2D hitBox            ;
    private Sound   som               ;
    private boolean estaComOMartelo   ;
    private int     height, width     ;
    private int     posicaoX, posicaoY;
    private int     vida              ;

    public Mario(){
        //Todo implements
    }

    public void mover(){
        //Todo implements
    }

    public void pular(){
        //Todo implements
    }


    public void atualizaVida(){
        //Todo implements
    }



    @Override
    public void atualizaImagem() {
        //Todo implements
    }

    @Override
    public void atualizaPosicao() {
        //Todo implements
    }

    public int getX(){
        return this.posicaoX;
    }

    public int getY(){
        return this.posicaoY;
    }

    public int getVida(){
        return  this.vida;
    }

    public void coletarMartelo(){
        this.estaComOMartelo = true;
    }

    public void perderMartelo(){
        this.estaComOMartelo = false;
    }

    public boolean EstaComOMartelo(){
        return estaComOMartelo;
    }

}
