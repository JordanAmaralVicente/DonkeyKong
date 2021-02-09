package com.donkeykong.models;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class Pontuacao {
    private int    pontuacaoInicial ;
    private final BitmapFont bitmapFont   ;
    private long  tempo;

    public Pontuacao(){
        this.pontuacaoInicial = 5000;
        this.bitmapFont = new BitmapFont();
    }

    public void atualizarPosicao(int index){
        //essa função só será chamada em casos específicos
        //esse index vai me ajudar a fazer uma verificação
        if(index == 0){
            //pulou inimigo
            this.pontuacaoInicial += 100;
        }else{
            //esmagou inimigo
            this.pontuacaoInicial += 800;
        }

    }

    public void reinicia(){
        this.pontuacaoInicial = 5000;
    }

    public void atualizaPontosPeloTempo(){
        //Aqui eu tenho que atualizar os pontos com o passar do tempo
        if(TimeUtils.millis() - tempo > 3300){
            pontuacaoInicial -= 150;
            tempo = TimeUtils.millis();
        }

    }

    public int getPontuacaoFinal(){
        return this.pontuacaoInicial;

    }

    public void draw(SpriteBatch batch){
        bitmapFont.draw(batch, "" + this.pontuacaoInicial, 100, 100);
    }

}
