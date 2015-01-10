package com.brassbeluga.momentum;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class GameInputProcessor extends InputAdapter {
	
	private World world;
	private Camera cam;
	
	private Vector3 touchPoint;
	
	public GameInputProcessor(World world, Camera cam) {
		this.world = world;
		this.cam = cam;
		touchPoint = new Vector3();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Convert the screen coordinates to world coordinates and pass to world
		touchPoint.set(screenX, screenY, 0f);
		touchPoint = cam.unproject(touchPoint);
		world.onTouchDown(touchPoint.x, touchPoint.y, pointer, button);
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { 
		// Convert the screen coordinates to world coordinates and pass to world
		touchPoint.set(screenX, screenY, 0f);
		touchPoint = cam.unproject(touchPoint);
		world.onTouchUp(touchPoint.x, touchPoint.y, pointer, button);
		return true;
	}
}
