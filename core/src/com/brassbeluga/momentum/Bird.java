package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bird extends GameObject {
	
	private float pegSpeed;
	private static final float PEG_BASE_SPEED = 0.02f;
	private GameSprite body;
	private GameSprite wing;
	private GameSprite eye;
	private float waveDelta;
	private float wavePeriod;

	/**
	 * Constructs a simple peg
	 * @param x The x position of the peg
	 * @param y The y position of the peg
	 * @param texture The texture to be used for the peg
	 */
	public Bird(float x, float y, TextureRegion texture, World world) {
		super(x, y, texture);
		this.pegSpeed = (float) (PEG_BASE_SPEED + Math.random() * PEG_BASE_SPEED * 4.0f);
		waveDelta = 0;
		wing = new GameSprite(Assets.birdWingDown, 0, 0);
		wing.centerOrigin();
		
		wing.addAnimation("flap", 14.0f * (PEG_BASE_SPEED / pegSpeed), Assets.birdWingDown, Assets.birdWingMid,
				Assets.birdWingTop, Assets.birdWingMid);
		eye = new GameSprite(Assets.birdEyeNormal, 0, 0);
		eye.centerOrigin();
		wing.playAnimation("flap", true);
		
		sprite.addChild(wing);
		sprite.addChild(eye);
	}
	
	@Override
	public void update(Vector2 gravity) {
		x += pegSpeed;
		waveDelta += (Math.PI / wing.getAnimationLength());
		sprite.angle = (float) (Math.abs(Math.sin(waveDelta) * 10f));
		if (x > World.WORLD_WIDTH +  sprite.bounds.x)
			x = -sprite.bounds.x / 2; 
		sprite.position.set(x, y);
	}

}
