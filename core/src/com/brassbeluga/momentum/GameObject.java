package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	public float x;
	public float y;
	public Rectangle bounds;
	public Vector2 velocity;
	public GameSprite sprite;
	
	public GameObject(float x, float y, TextureRegion texture) {
		this.x = x;
		this.y = y;
		Vector2 adjustedBounds = GameSprite.getAdjustedSpriteBounds(texture);
		this.bounds = new Rectangle(x - adjustedBounds.x / 2, y - adjustedBounds.y / 2, 
				adjustedBounds.x, adjustedBounds.y);
		sprite = new GameSprite(texture, x, y);
		sprite.centerOrigin();
		this.velocity = new Vector2(0f, 0f);
	}
	
	public void update(Vector2 gravity) {
		sprite.position.set(x, y);
	}
	
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
}
