package com.brassbeluga.momentum;

import com.badlogic.gdx.Game;

public class Momentum extends Game {
	
	@Override
	public void create () {
		Assets.load();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
