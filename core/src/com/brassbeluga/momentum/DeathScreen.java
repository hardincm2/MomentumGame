package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DeathScreen extends ScreenAdapter {
	
	public Stage stage;
	public Table table;
	public ShapeRenderer shapeRenderer;
	public int stages;
	
	private Momentum game;
	
	public DeathScreen(final Momentum game) {
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		stages = 0;
		
		shapeRenderer = new ShapeRenderer();
		
	}
	
	public void resize (int width, int height) {
	    stage.getViewport().update(width, height, true);
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(222 / 255f, 58 / 255f, 58 / 255f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		Assets.chunkBatch.begin();
		float height = Assets.chunkFont.getBounds("Test").height;
		Assets.drawText("You died!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height);
		Assets.chunkFont.setScale(0.8f);
		Assets.drawText("After " + stages + " Levels", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + height);
		Assets.chunkFont.setScale(0.4f);
		Assets.drawText("tap anywhere to start over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		Assets.chunkFont.setScale(1.0f);
		Assets.chunkBatch.end();
		
		if (Gdx.input.justTouched()) {
            game.setScreen(game.game);
        }
	}

	public void dispose() {
	    stage.dispose();
	    shapeRenderer.dispose();
	}
	
}
