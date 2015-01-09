package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Assets {

	private static TextureAtlas atlas;
	private static TextureAtlas uiAtlas;
	
	public static TextureRegion catBody;
	public static TextureRegion catTail;
	public static TextureRegion catTailLong;
	public static TextureRegion catTailCurl;
	
	public static TextureRegion peg;
	
	public static Music noodling;
	
	public static SpriteBatch chunkBatch;
	public static BitmapFont chunkFont;
	
	public static void load () {
		atlas = new TextureAtlas("momentum.pack");
		
		catBody = atlas.findRegion("cat_body");
		catTail = atlas.findRegion("cat_tail");
		catTailLong = atlas.findRegion("cat_tail_long");
		catTailCurl = atlas.findRegion("cat_tail_curl");
		peg = atlas.findRegion("peg");
		
		uiAtlas = new TextureAtlas("uiskin.atlas");
		
		noodling = Gdx.audio.newMusic(Gdx.files.internal("Noodling.mp3"));
		
		chunkBatch = new SpriteBatch();
		chunkFont = new BitmapFont(Gdx.files.internal("chunk.fnt"),
		         Gdx.files.internal("chunk.png"), false);
	}
	
	public static void drawText(SpriteBatch batch, String text, float x, float y) {
		drawText(batch, text, x, y, BitmapFont.HAlignment.LEFT);
	}
	
	public static void drawText(SpriteBatch batch, String text, float x, float y, BitmapFont.HAlignment align) {
		TextBounds width = Assets.chunkFont.getBounds(text);
		Assets.chunkFont.drawWrapped(batch, text, x - width.width / 2, y + width.height / 2, 
				Gdx.graphics.getWidth(), align);
	}
	
}
