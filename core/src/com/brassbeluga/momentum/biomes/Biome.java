package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brassbeluga.momentum.BackgroundSprite;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;

public abstract class Biome {

	/**
	 * The types of transition for transitioning from one biome to the next:
	 * OUT: This is the second to last frame of the current biome. IN: This is
	 * the second to last frame of the current biome, but the next biome is
	 * transitioning GONE: The current biome isn't drawing ground/background,
	 * but birds/background are still specific to current biome INTRO: This is
	 * the last frame of the current biome.
	 * 
	 * @author Spencer
	 * 
	 */
	public static enum Transition {
		NONE, OUT, IN, GONE, INTRO;
	}

	public World world;

	private boolean transFlipped = false;

	public Transition transType = Transition.NONE;

	// The offset of the background texture subtracted from their positions to
	// adjust for screen padding
	public static final float PADDING_OFFSET_X = (float) ((-25.0 / Gdx.graphics
			.getWidth()) * World.WORLD_WIDTH);
	public static final float PADDING_OFFSET_Y = (float) ((-25.0 / Gdx.graphics
			.getHeight()) * World.WORLD_HEIGHT);

	protected BackgroundSprite backTile;
	protected BackgroundSprite ground;
	protected BackgroundSprite backGround;
	protected BackgroundSprite groundTrans;
	protected BackgroundSprite backTrans;
	protected GameSprite startMound;
	public TextureRegion marker;

	/**
	 * Draw this biome to the screen.
	 */
	public void draw(SpriteBatch batch) {
		backGround.scrollSpeed = -world.player.velocity.x / 30.0f;
		backTrans.scrollSpeed = -world.player.velocity.x / 30.0f;
		ground.scrollSpeed = -world.player.velocity.x / 22.0f;
		groundTrans.scrollSpeed = -world.player.velocity.x / 22.0f;

		switch (transType) {
		case NONE:
			drawTiles(batch);
			backGround.draw(batch);
			ground.draw(batch);
			break;
		case OUT:
			drawTiles(batch);
			backGround.draw(batch);
			ground.draw(batch);
			break;
		case IN:
			groundTrans.draw(batch);
			break;
		case GONE:
			drawTiles(batch, 1.0f - world.player.x / World.WORLD_WIDTH);
			break;
		case INTRO:
			drawTiles(batch, world.player.x / World.WORLD_WIDTH);
			backTrans.draw(batch);
			ground.draw(batch);
			break;
		}
		if (world.level == 0)
			startMound.visible = true;
		else {
			startMound.visible = false;
			startMound.position.x = -PADDING_OFFSET_X;
		}

	}

	public void setTransitionType(Transition type) {
		if (type == Transition.IN) {
			if (!transFlipped) {
				groundTrans.texture.flip(true, false);
				transFlipped = true;
			}
		} else {
			if (transFlipped) {
				groundTrans.texture.flip(true, false);
				transFlipped = false;
			}
		}
		startMound.position.x = 0;
		groundTrans.position.x = -PADDING_OFFSET_X * 2.0f;
		backTrans.position.x = 0;
		transType = type;
	}

	private void drawTiles(SpriteBatch batch) {
		drawTiles(batch, 1.0f);
	}

	private void drawTiles(SpriteBatch batch, float alpha) {
		if (alpha < 0.0)
			alpha = 0.0f;
		if (alpha > 1.0)
			alpha = 1.0f;
		Color c = batch.getColor();
		// Set color for transparency
		batch.setColor(c.r, c.g, c.b, alpha);
		for (int i = 0; i < World.WORLD_WIDTH / backTile.bounds.x; i++) {
			batch.draw(backTile.texture, i * backTile.bounds.x, 0,
					backTile.bounds.x, backTile.bounds.y);
		}
		batch.setColor(c);
	}

	/**
	 * Allow the biome to update any assets it may need to.
	 * 
	 * @param delta
	 *            The time since update was last called.
	 */
	public void update(float delta) {
	}

	/**
	 * Called when a fresh biome needs to be generated (each new level)
	 */
	public void generate() {
	}
	
	/**
	 * Resets the biome to its initial state.
	 */
	public void reset() {
		ground.position.set(0, 0);
		
	}
	
}
