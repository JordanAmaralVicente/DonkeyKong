package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Macaco {
    private Texture imageRight;
    private Texture imageLeft;

    private Animation<TextureRegion> bateNoPeitoAnimation;
    private TextureRegion macacoFrame;
    private static final float BATER_FRAME_DURATION = 0.5f;
    float stateTime = 0f;

    public Sprite spriteMacaco;

    private int height;
    private int width;
    private int posicaoX;
    private int posicaoY;

    public void atualizaImagem(boolean direcao) {
//        if (direcao) {
//            this.spriteMacaco.setTexture(this.imageRight);
//        }
//        if (!direcao) {
//            this.spriteMacaco.setTexture(this.imageLeft);
//        }
    }

    public Macaco(int posX, int posY) {
        this.posicaoX = posX;
        this.posicaoY = posY;
    }

    public Macaco(int posX, int posY, int height, int width) {
        this.posicaoX = posX;
        this.posicaoY = posY;
        this.height = height;
        this.width = width;
        this.imageLeft = new Texture(Gdx.files.internal("personagens/donkey/donkey_left.png"));
        this.imageRight = new Texture(Gdx.files.internal("personagens/donkey/donkey_right.png"));
        this.spriteMacaco = new Sprite(this.imageRight);
        this.spriteMacaco.setPosition(this.posicaoX, this.posicaoY);
        this.spriteMacaco.setSize(this.width, this.height);

        loadTextures();
    }

    public void draw(SpriteBatch batch, float delta) {
        stateTime += delta;
        macacoFrame = bateNoPeitoAnimation.getKeyFrame(stateTime, true);
        spriteMacaco.setRegion(macacoFrame);
        this.spriteMacaco.draw(batch);
    }

    //https://github.com/libgdx/libgdx/wiki/2D-Animation
    //https://www.javacodegeeks.com/2013/02/android-game-development-with-libgdx-animation-part-2.html
    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/macacoAnimacao/macaco.txt"));

        TextureRegion[] baterNoPeito = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            baterNoPeito[i] = atlas.findRegion("macaco-0" + (i + 1));
        }
        bateNoPeitoAnimation = new Animation<>(BATER_FRAME_DURATION, baterNoPeito);

    }

}
