package com.donkeykong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.donkeykong.models.SimpleButton;

public class StartGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private SimpleButton btnStart;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture buttonSkin = new Texture(Gdx.files.internal("playImageButton.png"));
		btnStart = new SimpleButton(buttonSkin, 0, 0, 396, 180);

	}

	@Override
	public void render () {
		{//Início das configurações da janela do Jogo
			Gdx.gl.glClearColor(0, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.graphics.setTitle("Donkey Kong - Tela inicial");
			//Tamanho da janela: 640x480

		}//Fim das configurações de janela


		{//Início da criação das imagens na tela
			batch.begin();
			btnStart.update(batch, 0, 0);
			batch.end();
		}//Fim da criação das imagens na tela

		{//Início das verificações de input

			if(Gdx.input.isTouched()) { // Verifica se o Botão foi clicado através do mouse
				System.out.println("Input X: "+ Gdx.input.getX() +"\n Input Y: "+ Gdx.input.getY());
				btnStart.checkIfClicked(Gdx.input.getX(), Gdx.input.getY());
			}
		}//Fim das verificações de input

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
