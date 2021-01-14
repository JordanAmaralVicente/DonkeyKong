package com.donkeykong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.donkeykong.StartGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//Aqui é aonde o desktop chama o jogo. Localizado na pasta core
		//PS: Não precisamos Alterar NADA nessa classe
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Donkey Kong tela inicial";
		config.resizable = false;
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new StartGame(), config);
	}
}
