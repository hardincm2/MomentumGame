package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GameSprite implements Comparable {
	
	public TextureRegion texture;
	public Vector2 position;
	public Vector2 offset;
	public Vector2 scale;
	public float angle;
	public Vector2 bounds;
	public boolean visible;
	public boolean hasAbsoluteAngle;
	public int drawOrder;
	
	public ObjectMap<String, Animation> animations;
	public boolean looping;
	public int animIndex;
	public float animTime;
	public float animSpeed;
	public Animation anim;
	public boolean isRoot;
	
	public Matrix3 localTransform;
	public Matrix3 identity;
	
	public Array<GameSprite> children;
	public Array<GameSprite> drawList;

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
		looping = false;
		animIndex = 0;
		animTime = 0;
		animSpeed = 1.0f;
		children = new Array<GameSprite>();
		visible = true;
		hasAbsoluteAngle = false;
		isRoot = true;
		drawOrder = 0;
	}
	
	public void addAnimation(String name, Object ... info) {
		if (animations == null)
			animations = new ObjectMap<String, Animation>();
		animations.put(name, new Animation(info));
	}
	
	public void playAnimation(String name) {
		playAnimation(name, false);
	}
	
	public void playAnimation(String name, boolean looping) {
		anim = animations.get(name);
		animIndex = 0;
		animTime = anim.getLength(animIndex);
		texture = anim.getTexture(animIndex);
		this.looping = looping;
	}
	
	public float getAnimationLength() {
		float length = 0;
		if (anim != null) {
			for (int i = 0; i < anim.getFrames(); i++)
				length += anim.getLength(i);
		}
		return length;
	}
	
	public void draw(SpriteBatch batch) {
		// ***** unnecessary matrix creation
		draw(batch, identity);
	}
	
	// Update animations and transformations
	public void draw(SpriteBatch batch, Matrix3 parentTransform) {
		// Update transform matrix and update transforms in children
		if (visible) {
			updateTransform(parentTransform);
			// Draw the sprites with correct z-order from the root
			if (isRoot) {
				if (drawList != null) {
					for (GameSprite next : drawList)
						next.render(batch);
				}else
					render(batch);
			}
		}
		
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		//for (GameSprite sprite : children)
		//	sprite.visible = visible;
	}
	
	public void render(SpriteBatch batch) {
		// Update animation state
		if (anim != null) {
			animTime -= animSpeed;
			if (animTime <= 0) {
				if (animIndex < anim.getFrames() - 1) {
					animIndex++;
					animTime = anim.getLength(animIndex);
					texture = anim.getTexture(animIndex);
				}else if (looping) {
					animIndex = 0;
					animTime = anim.getLength(animIndex);
					texture = anim.getTexture(animIndex);
				}
			}
		}
		if (visible) {
			Vector2 pos = localTransform.getTranslation(new Vector2());
			batch.draw(texture, pos.x, pos.y, 0, 0, bounds.x * scale.x, bounds.y * scale.y, 1.0f, 1.0f, localTransform.getRotation());
		}
	}
	
	private void updateDrawList(Array<GameSprite> rootList) {
		if (drawList != null) {
			//if (visible) {
				rootList.addAll(drawList);
				for (GameSprite sprite : children)
					sprite.updateDrawList(rootList);
			//}
		}
	}
	
	public void updateTransform(Matrix3 parentTransform) {
		float spriteAngle = angle;
		float parAngle = parentTransform.getRotation();
		// Reverse the parent rotation if using absolute angles
		if (hasAbsoluteAngle)
			spriteAngle -= parAngle;
		Vector2 parTrans = parentTransform.getTranslation(new Vector2());
		// Apply parent then local transformations
		localTransform = localTransform.setToTranslation(parTrans).scale(scale).rotate(parAngle)
				.translate(position).rotate(spriteAngle).translate(-offset.x, -offset.y);
		localTransform = localTransform.translate(offset);
		
		// Update children
		for (GameSprite sprite : children)
			sprite.updateTransform(localTransform);
		
		localTransform = localTransform.translate(-offset.x, -offset.y);
	}
	
	protected static Vector2 getAdjustedSpriteBounds(TextureRegion texture) {
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
	
	public void addChild(GameSprite child) {
		if (child.isRoot) {
			if (drawList == null) {
				drawList = new Array<GameSprite>(true, 50);
				if (isRoot)
					drawList.add(this);
			}
			children.insert(0, child);
			child.isRoot = false;
			drawList.add(child);
			if (isRoot)
				child.updateDrawList(drawList);
			drawList.sort();
		}
	}

	@Override
	public int compareTo(Object o) {
		return ((GameSprite) o).drawOrder - drawOrder;
	}

}