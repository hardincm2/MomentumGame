package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {
	//World world;
	
	public GameScreen(final Momentum game) {
		
	}
	
	@Override
	public void render(float delta) {
		//world.update(delta);
		//world.render();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GameInputProcessor());
	}
}
