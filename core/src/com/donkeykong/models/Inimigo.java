package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.controllers.StartGame;

public class Inimigo extends Sprite {
    //Texturas
    private TextureRegion fogoFrame;


    //Animacoes
    private Animation<TextureRegion> moverEsquerdaAnimation;
    private Animation<TextureRegion> moverDireitaAnimation;
    private static final float MOVER_FRAME_DURATION = 0.3f;


    //Box2D
    public Body corpo;
    public World world;

    //Controle do tempo
    float stateTime = 0f;

    //Componentes e caracteristicas
    private final int posX;
    private final int posY;
    public Vector2 velocidade;
    private boolean visible = true;

    public Inimigo(World world, int posX, int posY, float velX, float velY) {
        super(new Texture(Gdx.files.internal("personagens/fogo/fogo_0.png")), 30, 30);
        this.world = world;
        this.posX = posX;
        this.posY = posY;

        velocidade = new Vector2(velX, velY);

        criaCorpoFogo();
        loadTextures();
    }

    protected void criaCorpoFogo() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(posX / StartGame.CONVERSAO_METRO_PIXEL, posY / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.shape = shape;
        fdef.density = 1;
        fdef.filter.categoryBits = BitsDeColisao.FOGO;
        corpo.setUserData("fogo");
        corpo.createFixture(fdef);
    }

    public void update(float delta) {
        stateTime += delta;

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);

        corpo.setLinearVelocity(velocidade);

        if (corpo.getLinearVelocity().x > 0 && corpo.getLinearVelocity().y == 0)
            fogoFrame = moverDireitaAnimation.getKeyFrame(stateTime, true);
        else if (corpo.getLinearVelocity().x < 0 && corpo.getLinearVelocity().y == 0)
            fogoFrame = moverEsquerdaAnimation.getKeyFrame(stateTime, true);

        //até onde cada fogo pode ir
        if (corpo.getPosition().x >= 650 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x > 0 && corpo.getPosition().y <= 160 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        } else if (corpo.getPosition().x < 15 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x < 0 && corpo.getPosition().y <= 160 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        } else if (corpo.getPosition().x >= 625 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x > 0 && corpo.getPosition().y <= 285 / StartGame.CONVERSAO_METRO_PIXEL && corpo.getPosition().y > 200 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        } else if (corpo.getPosition().x <= 45 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x < 0 && corpo.getPosition().y <= 285 / StartGame.CONVERSAO_METRO_PIXEL && corpo.getPosition().y > 200 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        } else if (corpo.getPosition().x >= 600 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x > 0 && corpo.getPosition().y <= 410 / StartGame.CONVERSAO_METRO_PIXEL && corpo.getPosition().y > 300 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        } else if (corpo.getPosition().x <= 65 / StartGame.CONVERSAO_METRO_PIXEL && velocidade.x < 0 && corpo.getPosition().y <= 410 / StartGame.CONVERSAO_METRO_PIXEL && corpo.getPosition().y > 300 / StartGame.CONVERSAO_METRO_PIXEL) {
            mudaDirecao();
        }

        //nunca deixa o fogo mudar sua posicao Y
        if (corpo.getPosition().y / StartGame.CONVERSAO_METRO_PIXEL != posY / StartGame.CONVERSAO_METRO_PIXEL)
            corpo.setTransform(corpo.getPosition().x, posY / StartGame.CONVERSAO_METRO_PIXEL, 0);


        setRegion(fogoFrame);

        if (!verificaVida()) { //caso seja necessario destruir o fogo
            world.destroyBody(corpo);
            visible = false;
        }

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

    public void mudaDirecao() {
        velocidade.x = -velocidade.x;
    }

    public boolean verificaVida() {
        //caso eles estejam marcados para serem destruidos
        if (corpo.getUserData().equals("destruir")) {
            visible = false;
            return false;
        }

        return true;

    }

    public boolean isVisible() {
        return visible;
    }

}


