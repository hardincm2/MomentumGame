package com.brassbeluga.momentum;

import com.badlogic.gdx.Game;

public class Momentum extends Game {
	
	public GameScreen game;
	public DeathScreen death;
	
	@Override
	public void create () {
		Assets.load();
		game = new GameScreen(this);
		death = new DeathScreen(this);
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void onDeath() {
		if (!this.getScreen().equals(death))
			setScreen(death);
	}
}
