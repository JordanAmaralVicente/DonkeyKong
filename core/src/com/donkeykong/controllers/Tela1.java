package com.donkeykong.controllers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.donkeykong.models.*;

import java.util.ArrayList;
import java.util.List;

public class Tela1 extends ScreenAdapter {

    Stage stage;
    StartGame game;
    Mario mario;
    Macaco donkeyKong;
    Princesa princesa;
    Cenario1 cenario1;
    List<Escada> stairs = new ArrayList<>(); //lista para mapear as posicoes das escadas
    boolean escada;

    Texture img;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    World world;
    Box2DDebugRenderer b2dr;

    public Tela1(StartGame game) {
        this.game = game;
        stage = new Stage(); //palco para os atores (mario e escada por enquanto)

        donkeyKong = new Macaco(10, 490, 50, 50); //DK
        mario = new Mario(stage, 25, 17, 40, 40); //MARIO
        cenario1 = new Cenario1(stage, 0, 0, 500, 500); //CENARIO DE FUNDO

        stairs.add(new Escada(421, 17, 12, 64)); //MAPEEI APENAS A PRIMEIRA ESCADA

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        game.cam.update();

        tiledMap = new TmxMapLoader().load("cenarios/img.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() /2 , rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() /2 , rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void render(float delta) {
        //handleInput(delta);

        tiledMapRenderer.setView(game.cam);
        tiledMapRenderer.render();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.cam.update();
        tiledMapRenderer.setView(game.cam);
        tiledMapRenderer.render();

        b2dr.render(world, game.cam.combined);

        {//Início da criação das imagens na tela
            game.batch.begin();
            //this.donkeyKong.draw(game.batch);
            game.batch.end();

            //stage.act(delta); //desenha todos os atores
            //stage.draw();
        }//Fim da criação das imagens na tela

    }//Fim das verificações de input

}

