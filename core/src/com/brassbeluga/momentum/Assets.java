package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

	private TextureAtlas atlas;
	
	public static Texture spider;
	public static Texture peg;
	
	public Assets () {
		atlas = new TextureAtlas("Momentum.pack");
		
		spider = atlas.createSprite("spider").getTexture();
		peg = atlas.createSprite("peg").getTexture();
	}
	
}
