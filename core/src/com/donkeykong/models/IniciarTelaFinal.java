package com.donkeykong.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.donkeykong.controllers.StartGame;

public class IniciarTelaFinal {

    //Tiledmap variaveis
    private TiledMap mapa;
    private TiledMapRenderer renderizadorMapa;

    public IniciarTelaFinal() {

        mapa = new TmxMapLoader().load("cenarios/img2.tmx");
        renderizadorMapa = new OrthogonalTiledMapRenderer(mapa, 1 / StartGame.CONVERSAO_METRO_PIXEL);

    }

    public TiledMapRenderer getRenderizadorMapa() {
        return renderizadorMapa;
    }

}


