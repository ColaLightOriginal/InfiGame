package com.puputan.infi;

import Screens.GameScreen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.puputan.infi.InfiGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Infi");
		config.setWindowedMode((int)GameScreen.WIDTH, (int)GameScreen.HEIGHT);
		new Lwjgl3Application(new InfiGame(), config);
	}
}
