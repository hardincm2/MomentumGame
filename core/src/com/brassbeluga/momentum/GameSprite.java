package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameSprite {
	
	public TextureRegion texture;
	public Vector2 position;
	public Vector2 offset;
	public Vector2 scale;
	public float angle;
	public Vector2 bounds;
	
	public Matrix3 localTransform;
	
	private Matrix3 localOrigin;
	private Matrix3 localRotation;
	private Matrix3 localScale;
	private Matrix3 localPosition;
	
	private Vector2 vecPos;
	private Vector2 vecScale;
	
	public Array<GameSprite> children;

	public GameSprite(TextureRegion texture, float x, float y) {
		this(texture, x, y, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0, 0);
		this.bounds = getAdjustedSpriteBounds(texture);
	}
	
	public GameSprite(TextureRegion texture, float x, float y, float originX, float originY,
			float scaleX, float scaleY, float angle, float width, float height) {
		this.texture = texture;
		this.position = new Vector2(x, y);
		this.offset = new Vector2(originX, originY);
		this.scale = new Vector2(scaleX, scaleY);
		this.angle = angle;
		this.bounds = new Vector2(width, height);
		this.localTransform = new Matrix3();
		this.localOrigin = new Matrix3();
		this.localPosition = new Matrix3();
		this.localRotation = new Matrix3();
		this.localScale = new Matrix3();
		this.vecPos = new Vector2();
		this.vecScale = new Vector2();
		children = new Array<GameSprite>();
	}

	public void draw(SpriteBatch batch) {
		// ***** unnecessary matrix creation
		draw(batch, new Matrix3().idt());
	}
	
	public void draw(SpriteBatch batch, Matrix3 parentTransform) {
		// Multiply the local transform by the parent transform
		Matrix3 trans = getLocalTransform().mul(parentTransform);
		vecPos = trans.getTranslation(vecPos);
		vecScale = trans.getScale(vecScale);
		batch.draw(texture, vecPos.x, vecPos.y, offset.x, offset.y, 
				vecScale.x * bounds.x, vecScale.y * bounds.y, 1.0f, 1.0f, trans.getRotation());
		// Draw the children with updated transform
		for (GameSprite sprite : children)
			sprite.draw(batch, trans);
	}
	
	protected Vector2 getAdjustedSpriteBounds(TextureRegion texture) {
		float width = (texture.getRegionWidth() * 1.0f / Gdx.graphics.getWidth()) * World.WORLD_WIDTH;
		float height = (texture.getRegionHeight() * 1.0f / Gdx.graphics.getHeight()) * World.WORLD_HEIGHT;
		return new Vector2(width, height);
	}
	
	public Matrix3 getLocalTransform() {
		localOrigin.setToTranslation(-offset.x, -offset.y);
		localRotation.setToRotation(angle);
		localScale.setToScaling(scale.x, scale.y);
		localPosition.setToTranslation(position.x, position.y);
		localTransform = localOrigin.mul(localPosition).mul(localRotation);
		return localTransform;
	}
	
	public void centerOrigin() {
		this.offset.set(bounds.x / 2, bounds.y / 2);
	}
	
	public void setOffset(int x, int y) {
		offset.set((x / texture.getRegionWidth()) * bounds.x, (y / texture.getRegionHeight()) * bounds.y);
	}

}