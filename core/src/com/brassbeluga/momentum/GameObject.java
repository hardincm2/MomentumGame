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
		
	}
	
}
