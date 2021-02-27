package com.donkeykong.models.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.visao.StartGame;
import com.donkeykong.models.BitsDeColisao;

public class GuardaChuva extends Sprite {
    public World mundo;

    private final int posicaoX;
    private final int posicaoY;
    public Body corpo;

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
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = BitsDeColisao.GUARDA_CHUVA;

        corpo.createFixture(fdef).setUserData("guardaChuva");
    }
}
