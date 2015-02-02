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
	
	public static TextureRegion catBody;
	public static TextureRegion catFaceNormal;
	public static TextureRegion catFaceBlink;
	public static TextureRegion catTail;
	public static TextureRegion catTailLong;
	public static TextureRegion catTailCurl;
	public static TextureRegion catTailRings;
	
	public static TextureRegion peg;
	
	public static Music noodling;
	
	public static SpriteBatch chunkBatch;
	public static BitmapFont chunkFont;
	
	public static void load () {
		// Load assets from file
		atlas = new TextureAtlas("momentum.atlas");
		
		catBody = atlas.findRegion("cat_body");
		catFaceNormal = atlas.findRegion("cat_face_normal");
		catFaceBlink = atlas.findRegion("cat_face_blink");
		catTail = atlas.findRegion("cat_tail");
		catTailLong = atlas.findRegion("cat_tail_long");
		catTailCurl = atlas.findRegion("cat_tail_curl");
		catTailRings = atlas.findRegion("cat_tail_rings");
		peg = atlas.findRegion("peg");
		
		noodling = Gdx.audio.newMusic(Gdx.files.internal("Noodling.mp3"));
		
		chunkBatch = new SpriteBatch();
		chunkFont = new BitmapFont(Gdx.files.internal("chunk.fnt"),
		         Gdx.files.internal("chunk.png"), false);
	}
	
	/**
	 * Draws text with the default font at a given position
	 * @param batch Current batch to draw the text to
	 * @param text The text to draw
	 * @param x Screen x-position
	 * @param y Screen y-position
	 */
	public static void drawText(SpriteBatch batch, String text, float x, float y) {
		TextBounds width = Assets.chunkFont.getBounds(text);
		Assets.chunkFont.drawWrapped(batch, text, x - width.width / 2, y + width.height / 2, 
				Gdx.graphics.getWidth(), BitmapFont.HAlignment.LEFT);
	}
	
}
