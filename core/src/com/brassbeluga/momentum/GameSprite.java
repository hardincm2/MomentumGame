package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Sprite object allowing parent-child sprite 
 * relationships for multiple sprites composited
 * together. Allows allows multiple-texture animations.
 * @author Spencer
 *
 */
public class GameSprite implements Comparable<GameSprite> {
	
	// Current texture of the sprite
	public TextureRegion texture;
	public Vector2 position;
	public Vector2 offset;
	public Vector2 scale;
	public float angle;
	public Vector2 bounds;
	
	// Whether or not the sprite is being drawn
	public boolean visible;
	
	// If true, the angle of the sprite will be independent of parent angle
	public boolean hasAbsoluteAngle;
	
	// The z-order of this sprite to be drawn in
	public int drawOrder;
	
	// Object map mapping animation names to animation objects
	public ObjectMap<String, Animation> animations;
	
	// Whether or not the animation is looping
	public boolean looping;
	// The current frame of the animation
	public int animIndex;
	// The time left on the current frame
	public float animTime;
	// The speed at the which the animation is playing
	public float animSpeed;
	// The current animation reference
	public Animation anim;
	
	// True if this sprite has no parent, false otherwise
	public boolean isRoot;
	
	// The local transform matrix of this sprite
	public Matrix3 localTransform;
	
	// An identity matrix for use in calculation
	public Matrix3 identity;
	
	// The sprites whose parent is this sprite
	public Array<GameSprite> children;
	
	// The sorted draworder list of children sprites
	public Array<GameSprite> drawList;

	/**
	 * Creates a new sprite with the given texture. The position refers to either 
	 * world coordinats (if root sprite) or offset coordinates (if child)
	 * @param textures
	 * @param x
	 * @param y
	 */
	public GameSprite(TextureRegion texture, float x, float y) {
		this(texture, x, y, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0, 0);
		this.bounds = getAdjustedSpriteBounds(texture);
		this.identity = new Matrix3().idt();
	}
	
	/**
	 * Creates a new sprite with given specifications.
	 * @param texture The texture of the new sprite
	 * @param x The x position of the sprite
	 * @param y The y position of the sprite
	 * @param originX The offset of the image origin from the x-position
	 * @param originY The offset of the image origin from the y-position
	 * @param scaleX The x-scale of the image
	 * @param scaleY The y-scale of the image
	 * @param angle The angle (in degrees) of the image
	 * @param width The width of the image bounds in world units
	 * @param height The height of the image bounds in world units
	 */
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
	
	/**
	 * Adds a new animation to the animation map (creating a new map
	 * if necessary). See Animation.java for information about input schemes.
	 * @param name The name of the new animation
	 * @param info The animation data for the new animation
	 */
	public void addAnimation(String name, Object ... info) {
		if (animations == null)
			animations = new ObjectMap<String, Animation>();
		animations.put(name, new Animation(info));
	}
	
	/**
	 * Plays the given animation if it exists
	 * @param name The name of the animation
	 */
	public void playAnimation(String name) {
		playAnimation(name, false);
	}
	
	/**
	 * Plays the given animation if it exists
	 * @param name The name of the animation
	 * @param looping Whether or not the animation should loop
	 */
	public void playAnimation(String name, boolean looping) {
		anim = animations.get(name);
		animIndex = 0;
		animTime = anim.getLength(animIndex);
		texture = anim.getTexture(animIndex);
		this.looping = looping;
	}
	
	/**
	 * Returns the total length of the animation currently playing.
	 * @return The length of the current animation
	 */
	public float getAnimationLength() {
		float length = 0;
		if (anim != null) {
			for (int i = 0; i < anim.getFrames(); i++)
				length += anim.getLength(i);
		}
		return length;
	}
	
	/**
	 * Draws the sprite to the given sprite batch. For used on 
	 * root sprite as there is no reference to a parent transform.
	 * The identity matrix is used.
	 * @param batch The sprite batch to draw the sprite to
	 */
	public void draw(SpriteBatch batch) {
		draw(batch, identity);
	}
	
	/**
	 * Draws the sprite to the given sprite batch. First updates the
	 * the transform matrices of every child sprite recursively. Then,
	 * if the calling sprite is the root sprite of the hierarchy, iterates
	 * through the draw order list and draws the sprites with the correct z-ordering.
	 * @param batch The sprite batch to draw the sprites to
	 * @param parentTransform The transform matrix of the parent sprite
	 */
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
	
	/**
	 * Sets this sprite's visibiliy, SHOULD (but doesn't) update
	 * all the children's visiblities. WIP
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		//for (GameSprite sprite : children)
		//	sprite.visible = visible;
	}
	
	/**
	 * Updates the animation logic and draws the texture at 
	 * the location, orientation, and scale determined by
	 * the local transform.
	 * @param batch The sprite batch to draw to
	 */
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
	public int compareTo(GameSprite o) {
		return o.drawOrder - drawOrder;
	}

}