package com.brassbeluga.momentum;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.controllers.HighscoreInputListener;
import com.brassbeluga.momentum.screens.DeathScreen;
import com.brassbeluga.momentum.screens.GameScreen;
import com.brassbeluga.momentum.screens.StartScreen;
import com.brassbeluga.momentum.stats.StatManager;

public class Momentum extends Game {
	
	public StatManager statManager;
	public GameScreen gameScreen;
	public DeathScreen deathScreen;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	
	@Override
	public void create () {
		// Load in all sounds, textures, etc...
		Assets.load();
		
		statManager = new StatManager();
		
		// Set up the camera.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		
		batch = new SpriteBatch();
		
		// Instantiate the different screen views that will be shown to the user.
		gameScreen = new GameScreen(this);
		deathScreen = new DeathScreen(this);
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	/**
	 * Should be called whenever the player has died in the world.
	 * 
	 * @param level The level that the player made it to.
 	 */
	public void onDeath(int level) {
		if (!this.getScreen().equals(deathScreen)) {
			deathScreen.setLevels(level);
			setScreen(deathScreen);
		}
	}
}
