package com.puputan.infi;

import Screens.GameScreen;
import com.badlogic.gdx.Game;

public class InfiGame extends Game {

	@Override
	public void create () {
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
	}
}
