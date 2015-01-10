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
		float width = (texture.getRegionWidth() * 1.0f / Gdx.graphics.getWidth()) * World.WORLD_WIDTH;
		float height = (texture.getRegionHeight() * 1.0f / Gdx.graphics.getHeight()) * World.WORLD_HEIGHT;
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
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
