package com.donkeykong.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.List;
import java.util.function.Predicate;

public class Mario implements Posicao, Imagem {
    private Texture img1, img2, img3, img4;
    private Image imageMario;
    private Rectangle hitBox; // a hitbox tem que ser um retangulo
    private Sound som;
    private boolean estaComOMartelo;
    private int height, width;
    private int posicaoX, posicaoY;
    private int vida;
    public static boolean status = true, canMove = true, climbing = false;

    public Mario(Stage stage, int posicaoX, int posicaoY, int width, int height) {
        img1 = new Texture(Gdx.files.internal("personagens/mario/mario_1.png"));
        img2 = new Texture(Gdx.files.internal("personagens/mario/mario_2.png"));
        img3 = new Texture(Gdx.files.internal("personagens/mario/mario_3.png"));
        img4 = new Texture(Gdx.files.internal("personagens/mario/mario_4.png"));

        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.width = width;
        this.height = height;

        hitBox = new Rectangle(this.posicaoX, this.posicaoY, this.width, this.height);

        imageMario = new Image(img1);
        imageMario.setPosition(this.posicaoX, this.posicaoY);
        imageMario.setSize(this.width, this.height);

        stage.addActor(imageMario);
    }

    public void mover(Acoes direcao, List<Escada> stairs) {

        if(!estaNaEscada(stairs)) climbing = false; // a variavel climbing foi criada por enquanto, pois nao encontrei uma
        //forma fácil de proibir o mario de se mover para os lados enquanto estiver na escada

        switch (direcao) {
            case MOVE_RIGHT: {
                if(!climbing){
                    posicaoX += 4;
                    hitBox.setPosition(posicaoX, posicaoY);
                    imageMario.addAction(Actions.moveTo(posicaoX, posicaoY));
                }
            }
            break;

            case MOVE_LEFT: {
                if(!climbing){
                    posicaoX -= 4;
                    hitBox.setPosition(posicaoX, posicaoY);
                    imageMario.addAction(Actions.moveTo(posicaoX, posicaoY));
                }
            }
            break;

            case MOVE_UP: {
                boolean response = estaNaEscada(stairs);
                System.out.println(response);

                if(response){
                    posicaoY += 2;
                    hitBox.setPosition(posicaoX, posicaoY);
                    imageMario.addAction(Actions.moveTo(posicaoX, posicaoY));
                    climbing = true;
                }
            }
            break;

            case MOVE_DOWN: {
                boolean response = estaNaEscada(stairs);

                if(response){
                    posicaoY -= 2;
                    hitBox.setPosition(posicaoX, posicaoY);
                    imageMario.addAction(Actions.moveTo(posicaoX, posicaoY));

                    if(posicaoY == 17) climbing = false;
                }
            }
            break;

            case MOVE_F: { //Teste para mapear as posições das escadas
                boolean response = estaNaEscada(stairs);
                System.out.println(response);
                System.out.println(posicaoX);
                System.out.println(posicaoY);
            }
            break;
        }
    }

    private boolean estaNaEscada(List<Escada> stairs) { //verifica se o mario pode subir ou descer
        Predicate<Escada> verifyStair = escada -> escada.overlaps(hitBox);
        return stairs.stream().anyMatch(verifyStair);
    }

    public void pular() {
        //Todo implements
    }

    public void atualizaVida() {
        //Todo implements
    }

    @Override
    public void atualizaImagem(boolean direcao) { //atualiza entre as duas imagens do mario. ainda faltam mais 2 para aimplementar
        if (direcao) {
            imageMario.setDrawable(new TextureRegionDrawable(new TextureRegion(img2)));
        }

        if (!direcao) {
            imageMario.setDrawable(new TextureRegionDrawable(new TextureRegion(img1)));
        }

        Mario.status = !Mario.status;
    }

    @Override
    public void atualizaPosicao(int x, int y) {
        imageMario.setPosition(x, y);
    }

    public int getX() {
        return this.posicaoX;
    }

    public int getY() {
        return this.posicaoY;
    }

    public int getVida() {
        return this.vida;
    }

    public void coletarMartelo() {
        this.estaComOMartelo = true;
    }

    public void perderMartelo() {
        this.estaComOMartelo = false;
    }

    public boolean EstaComOMartelo() {
        return estaComOMartelo;
    }

}
