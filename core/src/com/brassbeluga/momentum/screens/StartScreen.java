package com.brassbeluga.momentum.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.Momentum;
import com.brassbeluga.momentum.database.MomentumDB;

public class StartScreen extends ScreenAdapter {

	private Momentum game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float deltas;

	public StartScreen(final Momentum game) {
		this.game = game;
		this.camera = game.camera;
		this.batch = game.batch;
		deltas = 0.0f;
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(200 / 255f, 257 / 255f, 240 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltas += delta;
		camera.update();
		// batch.setProjectionMatrix(camera.combined);
		Assets.chunkBatch.begin();
		Assets.chunkBatch.draw(Assets.title, 0, 0);
		Assets.chunkBatch.draw(
				Assets.titleName,
				Gdx.graphics.getWidth() / 2.0f
						- Assets.titleName.getRegionWidth() / 2.0f,
				(float) Gdx.graphics.getHeight()
						- Assets.titleName.getRegionHeight() * 2.0f,
				(float) Assets.titleName.getRegionWidth() / 2.0f, 0.0f,
				(float) Assets.titleName.getRegionWidth(),
				(float) Assets.titleName.getRegionHeight(), 1.0f, 1.0f,
				(float) (4.5f * Math.sin(deltas)));
		Assets.chunkBatch.draw(
				Assets.titlePress,
				Gdx.graphics.getWidth() / 2.0f
						- Assets.titlePress.getRegionWidth() / 2.0f,
				Assets.titlePress.getRegionHeight() * 2.0f,
				(float) Assets.titlePress.getRegionWidth() / 2.0f, 0.0f,
				(float) Assets.titlePress.getRegionWidth(),
				(float) Assets.titlePress.getRegionHeight(), 1.0f, 1.0f,
				(float) (3.0f * Math.sin(deltas + 2)));
		Assets.chunkBatch.end();

		if (Gdx.input.justTouched()) {
			game.setScreen(game.gameScreen);
		}
	}

	public void dispose() {
	}
}
