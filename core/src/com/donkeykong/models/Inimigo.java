package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class Inimigo extends Sprite{
    public Body corpo;
    public World world;
    float stateTime = 0f;
    private Texture imageRight;
    private Texture imageLeft;
    private Animation<TextureRegion> moverEsquerdaAnimation;
    private Animation<TextureRegion> moverDireitaAnimation;
    private TextureRegion fogoFrame;
    public Sprite spriteFogo;
    private int posX, posY;
    private static final float MOVER_FRAME_DURATION = 0.3f;
    public Vector2 velocidade;

    private static short FOGO = 64;

    public Inimigo(World world, int posX, int posY, int velX, int velY ) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        criaCorpoFogo();
        this.imageLeft = new Texture(Gdx.files.internal("personagens/fogo/fogo_0.png"));
        this.imageRight = new Texture(Gdx.files.internal("personagens/fogo/fogo_1.png"));
        this.spriteFogo = new Sprite(this.imageRight);
        //setBounds(0, 0, 30, 30);

        loadTextures();
        velocidade = new Vector2(velX,velY);
    }

    protected void criaCorpoFogo() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(posX / StartGame.CONVERSAO_METRO_PIXEL, posY / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.filter.categoryBits = FOGO;
        corpo.createFixture(fdef).setUserData("fogo");
    }

    public void update(float delta) {
        stateTime += delta;
        corpo.setLinearVelocity(velocidade);
        //setPosition(corpo.getPosition().x - getWidth() / 2, corpo.getPosition().y - getHeight() / 2);

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);

        if (corpo.getLinearVelocity().x > 0 && corpo.getLinearVelocity().y == 0)
            fogoFrame = moverDireitaAnimation.getKeyFrame(stateTime, true);
        else if (corpo.getLinearVelocity().x < 0 && corpo.getLinearVelocity().y == 0)
            fogoFrame = moverEsquerdaAnimation.getKeyFrame(stateTime, true);

        if(corpo.getPosition().x >= 400 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x > 0){
            mudaDirecao();
        } else if (corpo.getPosition().x <= 0 && velocidade.x < 0){
            mudaDirecao();
        }

        if(corpo.getPosition().y / StartGame.CONVERSAO_METRO_PIXEL != posY / StartGame.CONVERSAO_METRO_PIXEL)
            corpo.setTransform(corpo.getPosition().x, posY / StartGame.CONVERSAO_METRO_PIXEL, 0);

        setRegion(fogoFrame);
    }

    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/fogoAnimacao/animacao.txt"));

        TextureRegion[] moverEsquerda = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            moverEsquerda[i] = atlas.findRegion("fogo-0" + (i + 1));
        }
        moverEsquerdaAnimation = new Animation<>(MOVER_FRAME_DURATION, moverEsquerda);

        TextureRegion[] moverDireita = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            moverDireita[i] = new TextureRegion(moverEsquerda[i]);
            moverDireita[i].flip(true, false);
        }

        moverDireitaAnimation = new Animation<>(MOVER_FRAME_DURATION, moverDireita);
    }

    public void mudaDirecao(){
        velocidade.x = -velocidade.x;
    }
}