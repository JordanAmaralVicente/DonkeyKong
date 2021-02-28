package com.donkeykong.models.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.visao.StartGame;
import com.donkeykong.models.BitsDeColisao;

public class Martelo extends Sprite {
    public World mundo;

    private final int posicaoX;
    private final int posicaoY;
    public Body corpo;

    private  boolean autoDestruir = false, visible = true;

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
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = BitsDeColisao.MARTELO;

        corpo.createFixture(fdef).setUserData("martelo");

    }

    public void render(){
        //se for necessário remover o martelo
        if(autoDestruir){
            mundo.destroyBody(corpo);
            autoDestruir = false;
            visible = false; //marcado para não ser mais desenhado
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAutoDestruir(boolean autoDestruir) {
        this.autoDestruir = autoDestruir;
    }
}
