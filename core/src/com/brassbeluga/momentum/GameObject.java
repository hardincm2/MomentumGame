package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	public float x;
	public float y;
	public float halfWidth;
	public float halfHeight;
	public Rectangle rect;
	public float angle;
	public Vector2 velocity;
	public TextureRegion region;
	
	public GameObject(float x, float y, float width, float height, TextureRegion texture) {
		this.x = x;
		this.y = y;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;
		this.rect = new Rectangle(-halfWidth, -halfHeight, width, height);
		this.region = texture;
		this.angle = 0f;
		this.velocity = new Vector2(0f, 0f);
	}
	
	public void update(Vector2 gravity) {
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(region, x + rect.x, y + rect.y, halfWidth, halfHeight, 
				rect.width, rect.height, 1.0f, 1.0f, angle);
	}
	
	public Vector2 getPosition(Vector2 position) {
		return position.set(x, y);
	}
	
}
