package com.donkeykong.models;

public class Pontuacao {
    private int    pontuacaoInicial ;
    private int    pontuacaoFinal   ;

    public Pontuacao(){
        this.pontuacaoFinal = 0;
        this.pontuacaoInicial = 5000;
    }

    public void atualizarPosicao(int index){
        //essa função só será chamada em casos específicos
        //esse index vai me ajudar a fazer uma verificação
        if(index == 0){
            //pulou inimigo
            this.pontuacaoFinal += 100;
        }else{
            //esmagou inimigo
            this.pontuacaoFinal += 800;
        }

    }

    public void reinicia(){
        this.pontuacaoFinal = 0;
        this.pontuacaoInicial = 5000;
    }

    public void atualizaPontosPeloTempo(){
        //Aqui eu tenho que atualizar os pontos com o passar do tempo
    }

    public int getPontuacaoFinal(){
        return this.pontuacaoFinal;
    }
}
