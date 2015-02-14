package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	public static TextureAtlas atlas;
	
	public static TextureRegion back_gradient_1;
	public static TextureRegion back_tile;
	
	public static TextureRegion catBody;
	public static TextureRegion catFaceNormal;
	public static TextureRegion catFaceBlink;
	public static TextureRegion catTail;
	public static TextureRegion catTailLong;
	public static TextureRegion catTailCurl;
	public static TextureRegion catTailRings;
	
	public static TextureRegion birdBody;
	public static TextureRegion birdWingDown;
	public static TextureRegion birdWingMid;
	public static TextureRegion birdWingTop;
	public static TextureRegion birdEyeNormal;
	public static TextureRegion birdEyeHeld;
	
	public static TextureRegion bush1;
	public static TextureRegion bush2;
	public static TextureRegion bush3;
	public static TextureRegion[] bushes;
	
	public static TextureRegion ground;
	public static TextureRegion start_mound;
	
	public static TextureRegion peg;
	
	public static Music noodling;
	
	public static SpriteBatch chunkBatch;
	public static BitmapFont chunkFont;
	
	public static ParticleEffect partFeathers;
	public static ParticleEffect partDirt;
	public static ParticleEffect partAir;
	
	public static float SCREEN_SCALE;

	public static final float DEV_WIDTH = 1024f;
	public static final float DEV_HEIGHT = 768f;
	// What percent of the screen height the cat should take up;
	public static final float PERC_CHARACTER = 0.2f;
	
	
	public static void load () {
	
		// Load assets from file
		atlas = new TextureAtlas("momentum.atlas");
		
		back_gradient_1 = atlas.findRegion("back_gradient");
		back_tile = atlas.findRegion("back_tile");
		
		catBody = atlas.findRegion("cat_body");
		catFaceNormal = atlas.findRegion("cat_face_normal");
		catFaceBlink = atlas.findRegion("cat_face_blink");
		catTail = atlas.findRegion("cat_tail");
		catTailLong = atlas.findRegion("cat_tail_long");
		catTailCurl = atlas.findRegion("cat_tail_curl");
		catTailRings = atlas.findRegion("cat_tail_rings");
		
		birdBody = atlas.findRegion("bird_body");
		birdWingDown = atlas.findRegion("bird_wing_down");
		birdWingMid = atlas.findRegion("bird_wing_mid");
		birdWingTop = atlas.findRegion("bird_wing_top");
		birdEyeNormal = atlas.findRegion("bird_eye_normal");
		birdEyeHeld = atlas.findRegion("bird_eye_held");
		
		bush1 = atlas.findRegion("bush1");
		bush2 = atlas.findRegion("bush2");
		bush3 = atlas.findRegion("bush3");
		bushes = new TextureRegion[3];
		bushes[0] = bush1;
		bushes[1] = bush2;
		bushes[2] = bush3;
		
		ground = new TextureRegion(new Texture(Gdx.files.internal("ground.png")));
		start_mound = new TextureRegion(new Texture(Gdx.files.internal("start_mound.png")));
		
		partFeathers = new ParticleEffect();
		partFeathers.load(Gdx.files.internal("part_feathers"), atlas);
		partDirt = new ParticleEffect();
		partDirt.load(Gdx.files.internal("part_dirt"), atlas);
		partAir = new ParticleEffect();
		partAir.load(Gdx.files.internal("part_air"), atlas);

		SCREEN_SCALE = (PERC_CHARACTER * Gdx.graphics.getHeight()) / ((float)catBody.getRegionHeight());
		
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
