package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen extends ScreenAdapter {
	
	private Momentum game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public StartScreen(final Momentum game, OrthographicCamera camera, SpriteBatch batch) {
		this.game = game;	
		this.camera = camera;
		this.batch = batch;
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(200 / 255f, 257 / 255f, 240 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		camera.update();
		//batch.setProjectionMatrix(camera.combined);
		
		Assets.chunkBatch.begin();
		float height = Assets.chunkFont.getBounds("Test").height;
		Assets.chunkFont.setColor(Color.PURPLE);
		Assets.chunkFont.setScale(1.5f);
		Assets.drawText(Assets.chunkBatch, "SWUNG", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height);
		Assets.chunkFont.setScale(0.8f);
		Assets.drawText(Assets.chunkBatch, "tap anywhere to start", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		Assets.chunkFont.setScale(1.0f);
		Assets.chunkFont.setColor(Color.WHITE);
		Assets.chunkBatch.end();
		
		if (Gdx.input.justTouched()) {
            game.setScreen(game.game);
        }
	}

	public void dispose() {
	}
}