package com.brassbeluga.momentum;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Class that allows the ability to generate a "rumbling" effect on the game
 * screen.
 * 
 * @author Cameron
 */
public class WorldRumbler {
	private OrthographicCamera camera;

	private Random random;
	private float rumbleX;
	private float rumbleY;
	private float rumbleTime = 0;
	private float currentRumbleTime = 1;
	private float rumblePower = 0;
	private float currentRumblePower = 0;

	/**
	 * Constructs a new world rumbler the will rumble 'camera' when called
	 * upon
	 */
	public WorldRumbler(OrthographicCamera camera) {
		this.camera = camera;
		random = new Random();
	}
	
	/**
	 * Rumble the screen!
	 * 
	 * @param power The magnitude of power of the rumble
	 * @param time The duration for the rumble
	 * @param delta The time since the last call to rumble
	 */
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
