package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TailObject extends GameObject {

	public Player parent;
	public Vector2 offset;
	private float lastAngle;
	public Vector2 texOff = new Vector2(60f, 12f);
	public Vector2 relOff;
	public Vector2 curlOff = new Vector2(-46f, -26f);
	private Vector2 longTailBounds;
	private Vector2 tailCurlBounds;
	
	public TailObject(Player parent, float x, float y, float xOff, float yOff, TextureRegion texture) {
		super(x, y, texture);
		texOff.set((texOff.x / texture.getRegionWidth()) * bounds.width, (texOff.y / texture.getRegionHeight()) * bounds.height);
		curlOff.set((curlOff.x / Assets.catTailCurl.getRegionWidth()) * bounds.width, (curlOff.y / Assets.catTailCurl.getRegionHeight()) * bounds.width);
		this.offset = new Vector2(xOff, yOff);
		relOff = offset.cpy();
		//relOff.add(texOff);
		this.parent = parent;
		lastAngle = parent.angle;
		//longTailBounds = getAdjustedSpriteBounds(Assets.catTailLong);
		//tailCurlBounds = getAdjustedSpriteBounds(Assets.catTailCurl);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		/*
		if (parent != null) {
			offset.rotate(parent.angle - lastAngle);
			texOff.rotate(parent.angle - lastAngle);
			relOff.rotate(parent.angle - lastAngle);
			lastAngle = parent.angle;
			if (parent.peg == null) {
				batch.draw(region, parent.x + offset.x - texOff.x, parent.y + offset.y - texOff.y, 0, 0, 
						bounds.width, bounds.height, 1.0f, 1.0f, parent.angle);
			}else{
				batch.draw(Assets.catTailLong, parent.x, parent.y, 0, longTailBounds.y / 2, 
						parent.swingRadius, longTailBounds.y, 1.0f, 1.0f, parent.pegAngle);
				batch.draw(Assets.catTailCurl, parent.peg.x + curlOff.x, parent.peg.y + curlOff.y, curlOff.x, curlOff.y, 
						tailCurlBounds.x, tailCurlBounds.y, 1.0f, 1.0f, 0);
			}
		}
		*/
	}

}
