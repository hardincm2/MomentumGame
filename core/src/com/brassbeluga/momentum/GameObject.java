package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	public Rectangle rect;
	public float angle;
	public Vector2 velocity;
	public TextureRegion region;
	public World world;
	
	public GameObject(float x, float y, float width, float height, TextureRegion texture, World world) {
		this.rect = new Rectangle(x, y, width, height);
		this.region = texture;
		this.angle = 0f;
		this.velocity = new Vector2(0f, 0f);
		this.world = world;
	}
	
	public void update() {
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(region, rect.x, rect.y, 0, 0, 
				rect.width, rect.height, 1.0f, 1.0f, angle);
	}
	
}
