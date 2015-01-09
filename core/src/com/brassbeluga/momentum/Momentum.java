package com.brassbeluga.momentum;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Momentum extends Game {
	
	public GameScreen game;
	public DeathScreen death;
	public SpriteBatch batch;
	
	public OrthographicCamera camera;
	
	@Override
	public void create () {
		Assets.load();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		batch = new SpriteBatch();
		game = new GameScreen(this, camera, batch);
		death = new DeathScreen(this, camera, batch);
		setScreen(new StartScreen(this, camera, batch));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void onDeath() {
		if (!this.getScreen().equals(death))
			setScreen(death);
	}
}
