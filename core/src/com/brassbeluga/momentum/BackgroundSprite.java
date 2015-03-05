package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;

/**
 * A sprite made exclusively for backgrounds that enables tiling
 * 
 * @author Spencer
 * 
 */
public class BackgroundSprite extends GameSprite {

	public boolean xWrap;
	public boolean yWrap;
	public float scrollSpeed;

	public BackgroundSprite(TextureRegion texture, float x, float y,
			boolean xWrap, boolean yWrap) {
		super(texture, x, y);
		this.texture = texture;
		this.xWrap = xWrap;
		this.yWrap = yWrap;
		this.scrollSpeed = 0.0f;
	}

	public BackgroundSprite(TextureRegion texture, float x, float y) {
		this(texture, x, y, true, false);
	}

	@Override
	public void render(SpriteBatch batch) {
		position.x += scrollSpeed;
		if (Math.abs(position.x) >= bounds.x)
			position.x = 0;
		batch.draw(texture, position.x, position.y, 0, 0, bounds.x * scale.x,
				bounds.y * scale.y, 1.0f, 1.0f, 0.0f);
		if (xWrap) {
			batch.draw(texture,
					position.x - bounds.x * Math.signum(position.x),
					position.y, 0, 0, bounds.x * scale.x, bounds.y * scale.y,
					1.0f, 1.0f, 0.0f);
		}
		for (GameSprite b : children) {
			Matrix3 localTrans = new Matrix3().setToTranslation(position);
			b.draw(batch, localTrans);
		}
	}

}
