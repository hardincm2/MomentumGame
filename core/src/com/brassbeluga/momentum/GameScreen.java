package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	private float accumulator;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	private ShapeRenderer shapes;
	
	
	public GameScreen(final Momentum game) {
		this.batch = game.batch;
		this.camera = game.camera;
		
		world = new World(game);
		accumulator = 0;
		shapes = new ShapeRenderer();
		shapes.setAutoShapeType(true);
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen and set a screen color.
		Gdx.gl.glClearColor(200 / 255f, 257 / 255f, 240 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera and sync the batch with the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Render the game to the spritebatch.
		batch.begin();
		world.render(batch);
		batch.end();
		
		Assets.chunkBatch.begin();
		Assets.chunkFont.setColor(Color.DARK_GRAY);
		Assets.drawText(Assets.chunkBatch, "" + world.level, 30, 30);
		Assets.chunkFont.setColor(Color.WHITE);
		Assets.chunkBatch.end();
		
		// Fixed time steps for predictable physics.
		accumulator += delta;
		while (accumulator >= STEP) {
			world.update(delta);
			accumulator -= delta;
		}
	}
	
	@Override
	public void show() {
		Assets.noodling.setLooping(true);
		Assets.noodling.play();
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
	}
	
	@Override
	public void hide() {
		Assets.noodling.stop();
		Gdx.input.setInputProcessor(null);
	}
}
