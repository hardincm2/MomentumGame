package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	private float accumulator;

	public World world;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	
	
	public GameScreen(final Momentum game) {
		world = new World();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		accumulator = 0;
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen and set a screen color.
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Fixed time steps for predictable physics.
		accumulator += delta;
		while (accumulator >= STEP) {
			world.update(delta);
			accumulator -= delta;
		}
		
		// Update the camera and sync the batch with the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		
		// Render the game to the spritebatch.
		batch.begin();
		world.render(batch);
		batch.end();
	}
	
	@Override
	public void show() {
		Assets.noodling.setLooping(true);
		Assets.noodling.play();
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
	}
}
