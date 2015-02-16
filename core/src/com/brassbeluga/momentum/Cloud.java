package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Cloud extends GameObject {

	private float speed = 0.01f;
	
	public Cloud(float x, float y, TextureRegion texture) {
		super(x, y, texture);
		speed = (float) (Math.random() * speed / 4.0f + speed);
	}
	
	@Override
	public void update(Vector2 gravity) {
		x += speed;
		sprite.position.set(x, y);
		
		if (x - sprite.bounds.x > World.WORLD_WIDTH)
			x = -sprite.bounds.x;
	}

}
