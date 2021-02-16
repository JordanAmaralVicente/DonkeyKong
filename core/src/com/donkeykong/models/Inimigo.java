package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;

public class Inimigo extends Sprite{
    public Body corpo;
    public World world;
    float stateTime = 0f;
    private Texture imageRight;
    private Texture imageLeft;
    private Animation<TextureRegion> moverAnimation;
    private TextureRegion fogoFrame;
    public Sprite spriteFogo;
    private static final float MOVER_FRAME_DURATION = 0.3f;

    public Inimigo(World world) {
        this.world = world;
        criaCorpoFogo();
        this.imageLeft = new Texture(Gdx.files.internal("personagens/fogo/fogo_0.png"));
        this.imageRight = new Texture(Gdx.files.internal("personagens/fogo/fogo_1.png"));
        this.spriteFogo = new Sprite(this.imageRight);
        setBounds(20, 70, 30, 30);
        loadTextures();
    }

    protected void criaCorpoFogo() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(90, 30);
        corpo = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        fdef.shape = shape;
        corpo.createFixture(fdef).setUserData("fogo");
    }

    public void update(float delta) {
        stateTime += delta;
        setPosition(corpo.getPosition().x - getWidth() / 2, corpo.getPosition().y - getHeight() / 2);
        fogoFrame = moverAnimation.getKeyFrame(stateTime, true);
        setRegion(fogoFrame);
    }

    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/fogoAnimacao/animacao.txt"));

        TextureRegion[] mover = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            mover[i] = atlas.findRegion("fogo-0" + (i + 1));
        }
        moverAnimation = new Animation<>(MOVER_FRAME_DURATION, mover);
    }
}