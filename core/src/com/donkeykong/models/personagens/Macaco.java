package com.donkeykong.models.personagens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class Macaco {

    private Animation<TextureRegion> bateNoPeitoAnimation;
    private static final float BATER_FRAME_DURATION = 0.5f;
    float stateTime = 0f;

    public Sprite spriteMacaco;

    private final int posicaoX;
    private final int posicaoY;


    public Macaco(int posX, int posY, int height, int width) {
        this.posicaoX = posX;
        this.posicaoY = posY;

        this.spriteMacaco = new Sprite();
        this.spriteMacaco.setPosition(this.posicaoX, this.posicaoY);
        this.spriteMacaco.setSize(width, height);

        loadTextures();
    }

    public void draw(SpriteBatch batch, float delta) {
        stateTime += delta;
        TextureRegion macacoFrame = bateNoPeitoAnimation.getKeyFrame(stateTime, true);
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
