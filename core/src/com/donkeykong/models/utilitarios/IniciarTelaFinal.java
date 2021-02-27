package com.donkeykong.models.utilitarios;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.donkeykong.visao.StartGame;

public class IniciarTelaFinal {

    private final TiledMapRenderer renderizadorMapa;

    public IniciarTelaFinal() {

        //Tiledmap variaveis
        TiledMap mapa = new TmxMapLoader().load("cenarios/img2.tmx");
        renderizadorMapa = new OrthogonalTiledMapRenderer(mapa, 1 / StartGame.CONVERSAO_METRO_PIXEL);

    }

    public TiledMapRenderer getRenderizadorMapa() {
        return renderizadorMapa;
    }

}


