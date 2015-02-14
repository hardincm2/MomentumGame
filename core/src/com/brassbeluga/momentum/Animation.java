package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Stores animation information and state of animation.
 * @author Spencer
 *
 */
public class Animation {
	
	// Array of frame textures and frame lengths
	private TextureRegion[] textures;
	private float[] times;
	
	/**
	 * Creates a new animation falling into one of three input
	 * schemes:
	 * 	- Evenly Spaced = { length, texture1, texture2, texture3, ... }
	 *  - Single Frame = { texture1 }
	 *  - Variable Spaced = { texture1, length1, texture2, length2, ... }
	 * @param data The data passed into the new animation
	 */
	public Animation(Object[] data) {
		if (data[0] instanceof Float) {
			// Evenly spaced
			loadEvenSpacing(data);
		}else if (data.length == 1) {
			// Single frame
			textures = new TextureRegion[1];
			textures[0] = (TextureRegion) data[0];
		} else {
			// Variable spaced
			loadCustomSpacing(data);
		}
	}
	
	/**
	 * Loads the frame data from the input, spacing all
	 * frames evenly with the given frame length.
	 * @param data The animation data being parsed
	 */
	private void loadEvenSpacing(Object[] data) {
		textures = new TextureRegion[data.length - 1];
		times = new float[data.length - 1];
		for (int i = 1; i < data.length; i++) {
			textures[i - 1] = (TextureRegion) data[i];
			times[i - 1] = (Float) data[0];
		}
	}
	
	/**
	 * Loads the frame data from the input, spacing all
	 * frames based on given frame lengths.
	 * @param data The animation data being parsed
	 */
	private void loadCustomSpacing(Object[] data) {
		textures = new TextureRegion[data.length / 2];
		times = new float[data.length / 2];
		for (int i = 0; i < data.length; i += 2) {
			textures[i / 2] = (TextureRegion) data[i];
			times[i / 2] = (Float) data[i + 1];
		}
	}
	
	/**
	 * Returns the number of frames in the animation
	 * @return The number of frames in the animation
	 */
	public int getFrames() {
		return textures.length;
	}
	
	/**
	 * Returns the texture of the frame at the given index
	 * @param index The index of the frame
	 * @return The specified frame's texture
	 */
	public TextureRegion getTexture(int index) {
		return textures[index % textures.length];
	}
	
	/**
	 * Returns the length of the frame at the given index
	 * @param index The index of the frame
	 * @return The specified frame's length
	 */
	public float getLength(int index) {
		if (times == null)
			return 0.0f;
		return times[index % times.length];
	}
	
}
