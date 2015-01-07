package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	public Rectangle rect;
	public float angle;
	public Vector2 velocity;
	public Texture texture;
	
	public GameObject(float x, float y, float width, float height, Texture texture) {
		this.rect = new Rectangle(x, y, width, height);
		this.texture = texture;
		this.angle = 0f;
		this.velocity = new Vector2(0f, 0f);
	}
	
	public void update() {
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, rect.x, rect.y, rect.x + rect.width / 2, rect.y + rect.height / 2, 
				rect.width, rect.height, 1.0f, 1.0f, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}
	
}
