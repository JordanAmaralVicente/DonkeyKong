package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class Mario extends Sprite {
    //Constantes
    private static final float RUNNING_FRAME_DURATION = 0.25f;
    public World mundo;

    //Controle de condicoes
    private Estado estadoAtual;
    private int contagemDeVidas;
    private boolean facingRight = true;
    private boolean estaComOMartelo;
    public static boolean estouNaEscada = false, estouNoChao = true;
    private int vida;

    //Texturas
    private TextureRegion marioParadoEsquerda;
    private TextureRegion marioParadoDireita;
    private TextureRegion marioEscalando;
    private TextureRegion marioPulandoEsquerda;
    private TextureRegion marioPulandoDireita;
    private TextureRegion marioFrame;

    //Animacoes
    private Animation<TextureRegion> andandoEsquerdaAnimacao;
    private Animation<TextureRegion> andandoDireitaAnimacao;
    float stateTime = 0f;

    //Componentes do personagem
    private Sound som;
    private int posicaoX, posicaoY;
    public Body corpo;

    public Mario(int posicaoX, int posicaoY, World mundo) {
        super(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-01.png")), 80, 42);
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.mundo = mundo;
        this.contagemDeVidas = 3;

        estadoAtual = Estado.PARADO_DIREITA;
        criaCorpoMario();
        loadTextures();
    }

    //https://github.com/libgdx/libgdx/wiki/2D-Animation
    //https://www.javacodegeeks.com/2013/02/android-game-development-with-libgdx-animation-part-2.html
    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/marioAnimacao/marioAnimacao.txt"));

        marioParadoEsquerda = atlas.findRegion("Mario-01");
        marioParadoDireita = new TextureRegion(marioParadoEsquerda);
        marioParadoDireita.flip(true, false);
        marioEscalando = new TextureRegion(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-04.png")));
        marioPulandoEsquerda = new TextureRegion(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-03.png")));
        marioPulandoDireita = new TextureRegion(marioPulandoEsquerda);
        marioPulandoDireita.flip(true, false);

        TextureRegion[] andandoEsquerdaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            andandoEsquerdaFrames[i] = atlas.findRegion("Mario-0" + (i + 1));
        }
        andandoEsquerdaAnimacao = new Animation<>(RUNNING_FRAME_DURATION, andandoEsquerdaFrames);

        TextureRegion[] andandoDireitaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            andandoDireitaFrames[i] = new TextureRegion(andandoEsquerdaFrames[i]);
            andandoDireitaFrames[i].flip(true, false);
        }
        andandoDireitaAnimacao = new Animation(RUNNING_FRAME_DURATION, andandoDireitaFrames);
    }

    //https://gamedev.stackexchange.com/questions/119143/how-to-fix-the-sprite-that-is-not-drawn-on-the-same-position-as-its-body-after-i
    public void criaCorpoMario() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox((getWidth() / 8f) / StartGame.CONVERSAO_METRO_PIXEL,
        //(getHeight() / 2f) / StartGame.CONVERSAO_METRO_PIXEL);
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;

        corpo.createFixture(fdef).setUserData("mario");
    }

    public void mover(float dt, int direcao) {
        switch (direcao) {
            case 19:
                if (Mario.estouNaEscada){
                    corpo.setLinearVelocity(new Vector2(0, 1.5f));
                }
                break;
            case 20:
                if (Mario.estouNaEscada){
                    corpo.setLinearVelocity(new Vector2(0, -1));
                }
                break;
            case 21:
                if((corpo.getPosition().x) >= 25/ 40f) {
                    corpo.setLinearVelocity(new Vector2(-1, 0));
                    facingRight = false;
                }
                break;
            case 22:
                System.out.println("posicao x"+ (corpo.getPosition().x));
                if((corpo.getPosition().x) <= 672/40f) {
                    corpo.setLinearVelocity(new Vector2(1, 0));
                    facingRight = true;
                }
                break;
//            case 62:
//                if (corpo.getLinearVelocity().y == 0 && !estouNaEscada && estouNoChao) {
//                    corpo.setLinearVelocity(new Vector2(0, 3f));
//                    estouNoChao = false;
//                }
//                break;
        }
    }

    public void update(float delta) {
        stateTime += delta;

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);

        if (corpo.getLinearVelocity().x > 0 && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.CORRENDO_DIREITA;
        else if (corpo.getLinearVelocity().x < 0 && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.CORRENDO_ESQUERDA;

        if (corpo.getLinearVelocity().x == 0 && facingRight && corpo.getLinearVelocity().y == 0)
            //estadoAtual = Estado.PARADO_DIREITA;
            //estadoAtual = corpo.getLinearVelocity().y != 0 ? Estado.ESCALANDO : Estado.PARADO_DIREITA;
            estadoAtual = Estado.PARADO_DIREITA;
        else if (corpo.getLinearVelocity().x == 0 && !facingRight && corpo.getLinearVelocity().y == 0)
            //estadoAtual = Estado.PARADO_ESQUERDA;
            //estadoAtual = corpo.getLinearVelocity().y != 0 ? Estado.ESCALANDO : Estado.PARADO_ESQUERDA;
            estadoAtual = Estado.PARADO_ESQUERDA;

        if (corpo.getLinearVelocity().y != 0 && !Mario.estouNaEscada && facingRight)
            estadoAtual = Estado.PULANDO_DIREITA;
        else if (corpo.getLinearVelocity().y != 0 && !Mario.estouNaEscada && !facingRight)
            estadoAtual = Estado.PULANDO_ESQUERDA;

        if (corpo.getLinearVelocity().y != 0 && Mario.estouNaEscada)
            estadoAtual = Estado.ESCALANDO;

        //Muda a sprite do MARIO
        switch (estadoAtual) {
            case PARADO_DIREITA:
                marioFrame = marioParadoDireita;
                break;
            case PARADO_ESQUERDA:
                marioFrame = marioParadoEsquerda;
                break;
            case CORRENDO_DIREITA:
                marioFrame = andandoDireitaAnimacao.getKeyFrame(stateTime, true);
                break;
            case CORRENDO_ESQUERDA:
                marioFrame = andandoEsquerdaAnimacao.getKeyFrame(stateTime, true);
                break;
            case ESCALANDO:
                marioFrame = marioEscalando;
                break;
            case PULANDO_ESQUERDA:
                marioFrame = marioPulandoEsquerda;
                break;
            case PULANDO_DIREITA:
                marioFrame = marioPulandoDireita;
                break;
        }

        setRegion(marioFrame);
    }

    public int getPosicaoX(){
        return this.posicaoX;
    }

    public int getPosicaoY(){
        return this.posicaoY;
    }

    public void diminuiVidas() {
        this.contagemDeVidas--;
    }

    public void resetaVidas() {
        this.contagemDeVidas = 3;
    }

}
