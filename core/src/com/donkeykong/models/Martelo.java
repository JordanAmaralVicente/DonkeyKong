package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class Martelo extends Sprite {
    public World mundo;

    private int posicaoX, posicaoY;
    public Body corpo;
    public static boolean autoDestruir;

    private static short MARTELO = 32;
    private boolean visible = true;

    public Martelo(int posicaoX, int posicaoY, World mundo) {
        super(new Texture(Gdx.files.internal("componentes/martelo.png")), 30, 46);
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.mundo = mundo;
        criaCorpoMartelo();

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);

    }

    private void criaCorpoMartelo() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox((getWidth() / 8f) / StartGame.CONVERSAO_METRO_PIXEL,
        //(getHeight() / 2f) / StartGame.CONVERSAO_METRO_PIXEL);
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = MARTELO;

        corpo.createFixture(fdef).setUserData("martelo");

    }

    public void update(){
        if(autoDestruir){
            mundo.destroyBody(corpo);
            autoDestruir = false;
            visible = false;

        }
    }

    public void adeusMartelo(){
        //setTexture(null);
        //mundo.destroyBody(corpo);
    }

    public boolean isVisible() {
        return visible;
    }
}
