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
	public boolean visible;
	public boolean hasAbsoluteAngle;
	
	public Matrix3 localTransform;
	public Matrix3 identity;
	
	public Array<GameSprite> children;

	public GameSprite(TextureRegion texture, float x, float y) {
		this(texture, x, y, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0, 0);
		this.bounds = getAdjustedSpriteBounds(texture);
		this.identity = new Matrix3().idt();
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
		children = new Array<GameSprite>();
		visible = true;
		hasAbsoluteAngle = false;
	}

	public void draw(SpriteBatch batch) {
		// ***** unnecessary matrix creation
		draw(batch, identity);
	}
	
	public void draw(SpriteBatch batch, Matrix3 parentTransform) {
		if (visible) {
			// Draw the children with updated transform
			float spriteAngle = angle;
			float parAngle = parentTransform.getRotation();
			if (hasAbsoluteAngle)
				spriteAngle -= parAngle;
			Vector2 parTrans = parentTransform.getTranslation(new Vector2());
			localTransform = localTransform.setToTranslation(parTrans).rotate(parAngle)
					.translate(position).rotate(spriteAngle).translate(-offset.x, -offset.y);
			Vector2 pos = localTransform.getTranslation(new Vector2());
			batch.draw(texture, pos.x, pos.y, 0, 0, bounds.x, bounds.y, 1.0f, 1.0f, localTransform.getRotation());
			
			localTransform = localTransform.translate(offset);
			
			for (GameSprite sprite : children)
				sprite.draw(batch, localTransform);
		}
		
	}
	
	protected Vector2 getAdjustedSpriteBounds(TextureRegion texture) {
		float width = (texture.getRegionWidth() * 1.0f / Gdx.graphics.getWidth()) * World.WORLD_WIDTH;
		float height = (texture.getRegionHeight() * 1.0f / Gdx.graphics.getHeight()) * World.WORLD_HEIGHT;
		return new Vector2(width, height);
	}
	
	public Matrix3 getLocalTransform() {
		return localTransform;
	}
	
	public void centerOrigin() {
		this.offset.set(bounds.x / 2, bounds.y / 2);
	}
	
	public void setOffset(int x, int y) {
		offset.set((x * 1.0f / texture.getRegionWidth()) * bounds.x, (y * 1.0f / texture.getRegionHeight()) * bounds.y);
	}

}