package com.donkeykong.models;

public class Pontuacao {
    private String texto            ;
    private int    pontuacaoInicial ;
    private int    pontuacaoFinal   ;

    public Pontuacao(){

    }

    public void atualizaPontuacao(){

    }

    public void reinicia(){
        this.pontuacaoFinal = 0;
        this.texto = "5000";
    }

}
