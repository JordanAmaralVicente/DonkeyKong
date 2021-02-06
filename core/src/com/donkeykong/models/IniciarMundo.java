package com.donkeykong.models;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class IniciarMundo {
    //Box2D variaveis
    private World mundo;
    private Box2DDebugRenderer renderizadorBox2D;

    //Tiledmap variaveis
    private TiledMap mapa;
    private TiledMapRenderer renderizadorMapa;

    public IniciarMundo() {
        //Primeiro o mundo
        mundo = new World(new Vector2(0, -10), true);
        renderizadorBox2D = new Box2DDebugRenderer();

        //Depois o mapa
        mapa = new TmxMapLoader().load("cenarios/img.tmx");
        renderizadorMapa = new OrthogonalTiledMapRenderer(mapa);

        //Renderizando objetos do mapa
        //Crio essas variaveis do lado de fora do for para não precisar declarar a cada passagem
        BodyDef bdef = new BodyDef(); //definicao do corpo box2D
        FixtureDef fdef = new FixtureDef(); //definicao da fixture do objeto
        PolygonShape shape = new PolygonShape(); //um atributo da fixture
        Body body; //corpo box2D

        //https://riptutorial.com/libgdx/example/17831/create-box2d-bodies-from-tiled-map
        for (MapObject object : mapa.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            //primeiro geramos as escadas, que no nosso mapa são representados pela camada 1

            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2),
                    (rect.getY() + rect.getHeight() / 2));
            body = mundo.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            fdef.isSensor = true; //isso serve para o mario não colidir com a escada
            body.createFixture(fdef).setUserData("escada");
        }

        for (MapObject object : mapa.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            //segundo geramos os blocos que representam o chão
            //como tudo é retangular, fica facil representar no jogo

            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2),
                    (rect.getY() + rect.getHeight() / 2));

            body = mundo.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            fdef.isSensor = false;
            body.createFixture(fdef).setUserData("chao");
        }

        //adiciono um novo listener para o mundo, que servira para informar em que objeto o mario está colidindo
        //servirá principalmente para subir as escadas
        //como a função listener faz tudo internamente, não é necessário gerar uma variavel para a interface, basta criar a instancia
        mundo.setContactListener(new ContactListener(mundo));
    }

    public Box2DDebugRenderer getRenderizadorBox2D() {
        return renderizadorBox2D;
    }

    public TiledMapRenderer getRenderizadorMapa() {
        return renderizadorMapa;
    }

    public World getMundo() {
        return mundo;
    }
}
