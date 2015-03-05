package com.brassbeluga.momentum.screens;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.Momentum;
import com.brassbeluga.momentum.controllers.HighscoreInputListener;
import com.brassbeluga.momentum.database.Highscore;
import com.brassbeluga.momentum.database.MomentumDB;
import com.google.gson.Gson;

public class DeathScreen extends ScreenAdapter {
	private static FileHandle localScoreFile = Gdx.files.local("scores/highscore.json");
	
	private Momentum game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private List<Highscore> highscores;
	private Highscore localScore;
	private int levels;
	
	public DeathScreen(final Momentum game) {
		this.game = game;
		this.camera = game.camera;
		this.batch = game.batch;
		highscores = null;
		
		try {
			String localJson = new String(localScoreFile.readBytes(), "UTF-8");
			localScore = new Gson().fromJson(localJson, Highscore.class);
		} catch (Exception e) {
			// File wasnt found so just assume this is the first time the user has scored.
			localScore = new Highscore("user", -1);
		}
		
		levels = 0;
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(222 / 255f, 58 / 255f, 58 / 255f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		float height = Assets.chunkFont.getBounds("Test").height;
		if (highscores == null) {
			// Draw the death message to screen.
			Assets.chunkBatch.begin();
			Assets.chunkFont.setColor(0f, 0f, 0f, 1.0f);
			Assets.drawText(Assets.chunkBatch, "You died!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height);
			Assets.chunkFont.setScale(0.8f);
			Assets.drawText(Assets.chunkBatch, "After " + levels + " Levels", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + height);
			Assets.drawText(Assets.chunkBatch, "Best record: " + localScore.score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			Assets.chunkFont.setScale(0.4f);
			Assets.drawText(Assets.chunkBatch, "tap anywhere to start over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - height);
			Assets.chunkFont.setScale(1.0f);
			Assets.chunkBatch.end();
		} else {
			Assets.chunkBatch.begin();
			for(int i = 0; i < Math.min(highscores.size(), 8); i++) {
				Highscore score = highscores.get(i);
				Assets.drawText(Assets.chunkBatch, "" + (i+1) + ". " + score.name + ": " + score.score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + (5 - i)* height);
			}
			Assets.chunkFont.setScale(0.8f);
			//Assets.chunkFont.setColor(1f, 1f, 0f, 1.0f);
			Assets.chunkBatch.end();
		}
		
		// Change back to the GameScreen if touched.
		if (Gdx.input.justTouched()) {
            game.setScreen(game.gameScreen);
        }
	}
	
	@Override
	public void show() {
		game.statManager.stats.highScore = levels;
		try {
			game.statManager.writeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Highscore currentScore = new Highscore("user",levels);
		
		if (currentScore.score > localScore.score) {
			String json = new Gson().toJson(currentScore);
			localScoreFile.writeBytes(json.getBytes(), false);
			localScore = currentScore;
			TextInputListener listener = new HighscoreInputListener(this);
			Gdx.input.getTextInput(listener, "New Highscore!!!", "user", null);
		} 
	}
	
	@Override
	public void hide() {
		highscores = null;
	}
	
	public void setLevels(int levels) {
		this.levels = levels;
	}
	
	public int getLevels() {
		return levels;
	}

	public List<Highscore> getHighscores() {
		return highscores;
	}

	public void setHighscores(List<Highscore> highscores) {
		this.highscores = highscores;
	}
	
}
