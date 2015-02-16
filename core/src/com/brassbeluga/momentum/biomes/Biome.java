package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;

public abstract class Biome {
	protected GameSprite backTile;
	protected GameSprite ground;
	protected GameSprite backGround;
	public TextureRegion marker;
	
	/**
	 * Draw this biome to the screen.
	 */
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < World.WORLD_WIDTH / backTile.bounds.x; i++) {
			batch.draw(backTile.texture, i * backTile.bounds.x, 0, backTile.bounds.x, backTile.bounds.y);
		}
		backGround.draw(batch);
		ground.draw(batch);
	}
	
	/**
	 * Allow the biome to update any assets it may need to.
	 * 
	 * @param delta The time since update was last called.
	 */
	public void update(float delta) {}
	
	/**
	 * Called when a fresh biome needs to be generated (each new level)
	 */
	public void generate() {}
}
