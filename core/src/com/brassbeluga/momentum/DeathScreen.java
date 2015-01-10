package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScreen extends ScreenAdapter {
	
	private Momentum game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private int levels;
	
	public DeathScreen(final Momentum game, OrthographicCamera camera, SpriteBatch batch) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		
		levels = 0;
		
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(222 / 255f, 58 / 255f, 58 / 255f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	    
		// Draw the death message to screen
		Assets.chunkBatch.begin();
		float height = Assets.chunkFont.getBounds("Test").height;
		Assets.drawText(Assets.chunkBatch, "You died!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height);
		Assets.chunkFont.setScale(0.8f);
		Assets.drawText(Assets.chunkBatch, "After " + levels + " Levels", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + height);
		Assets.chunkFont.setScale(0.4f);
		Assets.drawText(Assets.chunkBatch, "tap anywhere to start over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		Assets.chunkFont.setScale(1.0f);
		Assets.chunkBatch.end();
		
		// Change back to the GameScreen if touched
		if (Gdx.input.justTouched()) {
            game.setScreen(game.game);
        }
	}
	
	/**
	 * Sets the number of levels passed before death
	 * @param levels Number of levels passed
	 */
	public void setLevels(int levels) {
		this.levels = levels;
	}
	
}
