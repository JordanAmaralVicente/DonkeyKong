package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Mario extends Sprite {
    private final float VELOCIDADE_MAXIMA = 80f;

    private Estado estadoAtual;

    private static final float RUNNING_FRAME_DURATION = 0.25f;

    //Texturas
    private TextureRegion marioParadoEsquerda;
    private TextureRegion marioParadoDireita;
    private TextureRegion marioEscalando;
    private TextureRegion marioFrame;

    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;

    private Sound som;
    private boolean estaComOMartelo;
    private int height, width;
    private int posicaoX, posicaoY;
    private int vida;
    public Body corpo;
    public World world;
    float stateTime = 0f;
    public static boolean activateStair = false;

    public Mario(World world) {
        this.world = world;

        criaCorpoMario();
        setBounds(20, 70, 40, 40);
        estadoAtual = Estado.PARADO_DIREITA;

        loadTextures();
    }

    public Estado getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(Estado estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    //https://github.com/libgdx/libgdx/wiki/2D-Animation
    //https://www.javacodegeeks.com/2013/02/android-game-development-with-libgdx-animation-part-2.html
    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/marioAnimacao/animacao.txt"));

        marioParadoEsquerda = atlas.findRegion("Mario-01");
        marioParadoDireita = new TextureRegion(marioParadoEsquerda);
        marioParadoDireita.flip(true, false);
        marioEscalando = atlas.findRegion("Mario-04");

        TextureRegion[] walkLeftFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            walkLeftFrames[i] = atlas.findRegion("Mario-0" + (i + 2));
        }
        walkLeftAnimation = new Animation<>(RUNNING_FRAME_DURATION, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
    }

    public void criaCorpoMario() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(20, 70);
        corpo = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        fdef.shape = shape;
        corpo.createFixture(fdef).setUserData("mario");
    }

    public void update(float delta) {
        stateTime += delta;
        setPosition(corpo.getPosition().x - getWidth() / 2, corpo.getPosition().y - getHeight() / 2);

        if(corpo.getLinearVelocity().x == 0)
            estadoAtual = Estado.PARADO_DIREITA;

        if (corpo.getLinearVelocity().x > VELOCIDADE_MAXIMA) {
            corpo.setLinearVelocity(VELOCIDADE_MAXIMA, corpo.getLinearVelocity().y);
        }

        if (corpo.getLinearVelocity().x < -(VELOCIDADE_MAXIMA)) {
            corpo.setLinearVelocity(-(VELOCIDADE_MAXIMA), corpo.getLinearVelocity().y);
        }

        if(corpo.getLinearVelocity().x > 0){
            estadoAtual = Estado.CORRENDO_DIREITA;
        } else if (corpo.getLinearVelocity().x < 0){
            estadoAtual = Estado.CORRENDO_ESQUERDA;
        }

        if(corpo.getLinearVelocity().y > 0 || corpo.getLinearVelocity().y < 0){
            if(corpo.getLinearVelocity().x >= 0)
                estadoAtual = Estado.PARADO_DIREITA;
            else
                estadoAtual = Estado.PARADO_ESQUERDA;
        }

        if(activateStair)
            estadoAtual = Estado.ESCALANDO;

        switch (estadoAtual) {
            case PARADO_DIREITA:
                marioFrame = marioParadoDireita;
                break;
            case PARADO_ESQUERDA:
                marioFrame = marioParadoEsquerda;
                break;
            case CORRENDO_DIREITA:
                marioFrame = walkRightAnimation.getKeyFrame(stateTime, true);
                break;
            case CORRENDO_ESQUERDA:
                marioFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
                break;
            case ESCALANDO:
                marioFrame = marioEscalando;
                break;
        }

        setRegion(marioFrame);
    }


}
