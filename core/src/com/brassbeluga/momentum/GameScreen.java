package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {
	World world;
	OrthographicCamera camera;
	SpriteBatch batch;
	
	public GameScreen(final Momentum game) {
		world = new World();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
	}
	
	@Override
	public void render(float delta) {
		// Set screen color.
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.5f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		world.update(delta);
		
		// draw shit.
		batch.begin();
		world.render(batch);
		batch.end();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GameInputProcessor());
	}
}
