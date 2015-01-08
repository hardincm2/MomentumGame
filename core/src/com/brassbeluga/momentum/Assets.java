package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	private static TextureAtlas atlas;
	
	public static TextureRegion spider;
	public static TextureRegion peg;
	
	public static void load () {
		atlas = new TextureAtlas("Momentum.pack");
		
		spider = atlas.findRegion("spider");
		peg = atlas.findRegion("peg");
	}
	
}
