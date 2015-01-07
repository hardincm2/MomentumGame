package com.brassbeluga.momentum;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
	
	public GameInputProcessor() {
		
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { 
		
		return true;
	}
}
