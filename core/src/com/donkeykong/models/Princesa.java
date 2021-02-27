package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class Princesa {
    public Sprite spritePrincesa;
    public Body corpo;
    private int posX, posY, height, width;
    private World world;
    private static final short PRINCESA = 500;

    public Princesa(int posX, int posY, int height, int width, World world){
        this.spritePrincesa = new Sprite(new Texture(Gdx.files.internal("personagens/princesa/princesa.png")));
        this.spritePrincesa.setSize(width, height);
        this.spritePrincesa.setPosition(posX, posY);
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
        this.world = world;
        criaCorpoPrincesa();
    }

    private void criaCorpoPrincesa() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        float posXIncrement = this.posX + this.width + 30;
        float posYIncrement = this.posY + this.height/2;

        bdef.position.set(posXIncrement/ StartGame.CONVERSAO_METRO_PIXEL, posYIncrement / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        float widthIncrement = this.width * 20;
        float heightIncrement = this.height * 3/2;

        shape.setAsBox((widthIncrement / 8f) / StartGame.CONVERSAO_METRO_PIXEL,
        (heightIncrement / 2f) / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = PRINCESA;

        corpo.createFixture(fdef).setUserData("princesa");
    }

    public void draw(SpriteBatch batch) {
        this.spritePrincesa.draw(batch);
    }

}
