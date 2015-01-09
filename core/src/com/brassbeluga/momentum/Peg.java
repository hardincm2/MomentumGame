package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Peg extends GameObject {

	/**
	 * Constructs a simple peg
	 * @param x The x position of the peg
	 * @param y The y position of the peg
	 * @param texture The texture to be used for the peg
	 */
	public Peg(float x, float y, TextureRegion texture, World world) {
		super(x, y, texture);
	}

}
