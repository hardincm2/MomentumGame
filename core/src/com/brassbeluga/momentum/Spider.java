package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spider extends GameObject {

	public Peg peg;
	public float angVel;
	
	public Spider(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height, texture);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, rect.x, rect.y, rect.x + rect.width / 2, rect.y + rect.height / 2, rect.width, rect.height, 1.0f, 1.0f, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}

}
