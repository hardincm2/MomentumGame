package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

	private static TextureAtlas atlas;
	
	public static Texture spider;
	public static Texture peg;
	
	public static void load () {
		atlas = new TextureAtlas("Momentum.pack");
		
		spider = atlas.createSprite("spider").getTexture();
		peg = atlas.createSprite("peg").getTexture();
	}
	
}
