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
	
	public int stages;
	
	public DeathScreen(final Momentum game, OrthographicCamera camera, SpriteBatch batch) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		
		stages = 0;
		
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(222 / 255f, 58 / 255f, 58 / 255f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	    
		Assets.chunkBatch.begin();
		float height = Assets.chunkFont.getBounds("Test").height;
		Assets.drawText(Assets.chunkBatch, "You died!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height);
		Assets.chunkFont.setScale(0.8f);
		Assets.drawText(Assets.chunkBatch, "After " + stages + " Levels", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + height);
		Assets.chunkFont.setScale(0.4f);
		Assets.drawText(Assets.chunkBatch, "tap anywhere to start over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		Assets.chunkFont.setScale(1.0f);
		Assets.chunkBatch.end();
		
		if (Gdx.input.justTouched()) {
            game.setScreen(game.game);
        }
	}
	
}
