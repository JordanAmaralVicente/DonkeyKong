package com.donkeykong.models.objetos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Pontuacao {
    private int    pontuacaoInicial;
    private final BitmapFont bitmapFont;
    private long  tempo;

    public Pontuacao(){
        this.pontuacaoInicial = 5000;
        this.bitmapFont = new BitmapFont();
        this.bitmapFont.getData().setScale(2, 2);
        this.bitmapFont.setColor(Color.PURPLE);
    }

    public void atualizarPontuacao(int index){
        //essa função só será chamada em casos específicos
        //esse index vai me ajudar a fazer uma verificação
        if(index == 1){
            this.pontuacaoInicial += 800;
        }
    }

    /** name: atualizaPontosPeloTempo
     *  | type: public
     *  | return: boolean
     *  | Função que vai atualizar a pontuação do jogador de acordo com o tempo
     *  e retorna verdadeiro ou falso de acordo com os pontos
     *  | true: se o jogador, após atualizar o tempo, tem pontos positivos
     *  | false: se o jogador, após atualizar o tempo, não tem pontos
     * */
    public boolean atualizaPontosPeloTempo(){
        if(TimeUtils.millis() - tempo > 3300 ){
            pontuacaoInicial -= 150;
            tempo = TimeUtils.millis();
            return pontuacaoInicial > 0;
        }
        return true;
    }

    public void draw(SpriteBatch batch){
        bitmapFont.draw(batch, "" + this.pontuacaoInicial, 480, 690);
    }

}
