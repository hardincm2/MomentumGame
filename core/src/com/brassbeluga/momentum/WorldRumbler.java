package com.brassbeluga.momentum;

import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Class that allows the ability to generate a "rumbling" effect on the game
 * screen.
 * 
 * @author Cameron
 */
public class WorldRumbler {
	private OrthographicCamera camera;
	private World world;

	private Random random;
	private float rumbleX;
	private float rumbleY;
	private float rumbleTime = 0;
	private float currentRumbleTime = 1;
	private float rumblePower = 0;
	private float currentRumblePower = 0;

	public WorldRumbler(OrthographicCamera camera) {
		this.camera = camera;
		random = new Random();
	}

	public void rumble(float power, float time, float delta) {
		rumblePower = power;
		rumbleTime = time;
		currentRumbleTime = 0;

		currentRumblePower = rumblePower
				* ((rumbleTime - currentRumbleTime) / rumbleTime);
		rumbleX = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
		rumbleY = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;

		camera.translate(-rumbleX, -rumbleY);
		currentRumbleTime += delta;
	}
}
