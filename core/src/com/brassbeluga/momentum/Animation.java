package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[] textures;
	private float[] times;
	
	public Animation(Object[] data) {
		if (data[0] instanceof Float) {
			loadEvenSpacing(data);
		}else if (data.length == 1) {
			textures = new TextureRegion[1];
			textures[0] = (TextureRegion) data[0];
		} else {
			loadCustomSpacing(data);
		}
	}
	
	private void loadEvenSpacing(Object[] data) {
		textures = new TextureRegion[data.length - 1];
		times = new float[data.length - 1];
		for (int i = 1; i < data.length; i++) {
			textures[i] = (TextureRegion) data[i];
			times[i] = (Float) data[0];
		}
	}
	
	private void loadCustomSpacing(Object[] data) {
		textures = new TextureRegion[data.length / 2];
		times = new float[data.length / 2];
		for (int i = 0; i < data.length; i += 2) {
			textures[i / 2] = (TextureRegion) data[i];
			times[i / 2] = (Float) data[i + 1];
		}
	}
	
	public int getFrames() {
		return textures.length;
	}
	
	public TextureRegion getTexture(int index) {
		return textures[index % textures.length];
	}
	
	public float getLength(int index) {
		if (times == null)
			return 0.0f;
		return times[index % times.length];
	}
	
}
