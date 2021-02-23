package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class GuardaChuva extends Sprite {
    public World mundo;

    private int posicaoX, posicaoY;
    public Body corpo;

    private static short GUARDA_CHUVA = 64;

    public GuardaChuva(int posicaoX, int posicaoY, World mundo) {
        super(new Texture(Gdx.files.internal("componentes/guardaChuva.png")), 30, 28);
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.mundo = mundo;

        criaCorpoGuardaChuva();

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);
    }

    private void criaCorpoGuardaChuva() {
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
        fdef.filter.categoryBits = GUARDA_CHUVA;

        corpo.createFixture(fdef).setUserData("guardaChuva");
    }
}
