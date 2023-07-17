package com.puputan.infi;

import Screens.GameScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.puputan.infi.Listeners.ContactListener;
import com.puputan.infi.Objects.*;
import com.puputan.infi.Processors.GameInputProcessor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

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
