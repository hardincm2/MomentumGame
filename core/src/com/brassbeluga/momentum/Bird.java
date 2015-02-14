package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bird extends GameObject {
	
	private float pegSpeed;
	private static final float PEG_BASE_SPEED = 0.01f;
	private static final float HELD_FLAP_SPEED = 4.0f;
	private GameSprite body;
	private GameSprite wing;
	private GameSprite eye;
	private float waveDelta;
	private float wavePeriod;
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
		this.pegSpeed = (float) (PEG_BASE_SPEED + Math.random() * PEG_BASE_SPEED * 4.0f);
		waveDelta = 0;
		wing = new GameSprite(Assets.birdWingDown, 0, 0);
		wing.centerOrigin();
		
		wing.addAnimation("flap", 20.0f, Assets.birdWingDown, Assets.birdWingMid,
				Assets.birdWingTop, Assets.birdWingMid);
		eye = new GameSprite(Assets.birdEyeNormal, 0, 0);
		eye.addAnimation("normal", Assets.birdEyeNormal);
		eye.addAnimation("held", Assets.birdEyeHeld);
		eye.centerOrigin();
		wing.playAnimation("flap", true);
		wing.animSpeed = baseAnimSpeed = (pegSpeed / PEG_BASE_SPEED);
		
		sprite.addChild(wing);
		sprite.addChild(eye);
		
		held = false;
	}
	
	@Override
	public void update(Vector2 gravity) {
		if (held) {
			wing.animSpeed = HELD_FLAP_SPEED;
			waveDelta += (Math.PI * wing.animSpeed / wing.getAnimationLength());
			sprite.angle = (float) (Math.abs(Math.sin(waveDelta) * 20f));
			eye.playAnimation("held");
		} else {
			wing.animSpeed = baseAnimSpeed;
			waveDelta += (Math.PI * wing.animSpeed / wing.getAnimationLength());
			sprite.angle = (float) (Math.abs(Math.sin(waveDelta) * 10f));
			if (x > World.WORLD_WIDTH + sprite.bounds.x)
				x = -sprite.bounds.x;
			eye.playAnimation("normal");
		}
		x += pegSpeed;
		
		sprite.position.set(x, y);
	}

}
