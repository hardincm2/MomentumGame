package com.brassbeluga.momentum.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.brassbeluga.momentum.GameSprite;

public class GameObject {

	public float x;
	public float y;
	public Rectangle bounds;
	public Vector2 velocity;
	public GameSprite sprite;
	
	/**
	 * Creates a new game object at the given x,y position. Sets
	 * the new sprite's texture to the given texture with a centered
	 * origin.
	 * @param x
	 * @param y
	 * @param texture The texture reference for the new object
	 */
	public GameObject(float x, float y, TextureRegion texture) {
		this.x = x;
		this.y = y;
		Vector2 adjustedBounds = GameSprite.getAdjustedSpriteBounds(texture);
		// Set collision bounds to sprite's bounds
		this.bounds = new Rectangle(x - adjustedBounds.x / 2, y - adjustedBounds.y / 2, 
				adjustedBounds.x, adjustedBounds.y);
		sprite = new GameSprite(texture, x, y);
		sprite.centerOrigin();
		this.velocity = new Vector2(0f, 0f);
	}
	
	/**
	 * Updates this objects game logic. Called each step
	 * after render.
	 * @param gravity The world's gravity vector
	 */
	public void update(Vector2 gravity) {
		sprite.position.set(x, y);
	}
	
	/**
	 * Renders this object to the given sprite batch. Called
	 * each step before updating logic.
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
}
