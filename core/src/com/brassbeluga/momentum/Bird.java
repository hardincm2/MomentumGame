package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bird extends GameObject {
	
	private float pegSpeed;
	// Base horizontal speed for a bird
	private static final float PEG_BASE_SPEED = 0.01f;
	// Animation speed for a bird being held onto
	private static final float HELD_FLAP_SPEED = 4.0f;
	private GameSprite wing;
	private GameSprite eye;
	private float waveDelta;
	private float baseAnimSpeed;
	
	public boolean held;

	/**
	 * Constructs a simple peg
	 * @param x The x position of the peg
	 * @param y The y position of the peg
	 * @param texture The texture to be used for the peg
	 */
	public Bird(float x, float y, TextureRegion texture) {
		super(x, y, texture);
		
		// Randomly vary the speed
		this.pegSpeed = (float) (PEG_BASE_SPEED + Math.random() * PEG_BASE_SPEED * 4.0f);
		
		// Start the sin wave progression for rotation
		waveDelta = 0;
		
		// Load sprite components
		wing = new GameSprite(Assets.birdWingDown, 0, 0);
		wing.centerOrigin();
		
		// Add the birds animation
		wing.addAnimation("flap", 20.0f, Assets.birdWingDown, Assets.birdWingMid,
				Assets.birdWingTop, Assets.birdWingMid);
		eye = new GameSprite(Assets.birdEyeNormal, 0, 0);
		eye.addAnimation("normal", Assets.birdEyeNormal);
		eye.addAnimation("held", Assets.birdEyeHeld);
		eye.centerOrigin();
		wing.playAnimation("flap", true);
		
		// Set the animations speed relative to the horizontal speed
		wing.animSpeed = baseAnimSpeed = (pegSpeed / PEG_BASE_SPEED);
		
		sprite.addChild(wing);
		sprite.addChild(eye);
		
		held = false;
	}
	
	@Override
	public void update(Vector2 gravity) {
		
		// If the player is swinging by this bird
		if (held) {
			
			// Set the animation speed to the rapid, held speed
			wing.animSpeed = HELD_FLAP_SPEED;
			
			// Speed up the rate of rotation
			waveDelta += (Math.PI * wing.animSpeed / wing.getAnimationLength());
			sprite.angle = (float) (Math.abs(Math.sin(waveDelta) * 20f));
			
			// Change the bird's eye animation
			eye.playAnimation("held");
			
		} else {
			
			// Reset the animation speed to the original animation speed
			wing.animSpeed = baseAnimSpeed;
			waveDelta += (Math.PI * wing.animSpeed / wing.getAnimationLength());
			sprite.angle = (float) (Math.abs(Math.sin(waveDelta) * 10f));
			
			eye.playAnimation("normal");
		}
		
		// Update position
		x += pegSpeed;
		
		// Update sprite
		sprite.position.set(x, y);
	}

}
