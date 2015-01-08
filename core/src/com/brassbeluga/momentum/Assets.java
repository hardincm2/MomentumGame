package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	private static TextureAtlas atlas;
	
	public static TextureRegion spider;
	public static TextureRegion peg;
	
	public static Music noodling;
	
	public static void load () {
		atlas = new TextureAtlas("Momentum.pack");
		
		spider = atlas.findRegion("spider");
		peg = atlas.findRegion("peg");
		
		noodling = Gdx.audio.newMusic(Gdx.files.internal("Noodling.mp3"));
	}
	
}
