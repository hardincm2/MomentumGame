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
	
	// Background textures
	public static TextureRegion back_gradient_1;
	public static TextureRegion back_tile_hills;
	public static TextureRegion back_tile_forest;
	public static TextureRegion back_dist_hills;
	public static TextureRegion back_dist_forest_trees;
	public static TextureRegion back_dist_forest_fartrees;
	
	
	
	// Cat textures
	public static TextureRegion catBody;
	public static TextureRegion catFaceNormal;
	public static TextureRegion catFaceBlink;
	public static TextureRegion catTail;
	public static TextureRegion catTailLong;
	public static TextureRegion catTailCurl;
	public static TextureRegion catTailRings;
	
	// Bird textures
	public static TextureRegion birdBody;
	public static TextureRegion birdWingDown;
	public static TextureRegion birdWingMid;
	public static TextureRegion birdWingTop;
	public static TextureRegion birdEyeNormal;
	public static TextureRegion birdEyeHeld;
	
	// Bush textures
	public static TextureRegion bush1;
	public static TextureRegion bush2;
	public static TextureRegion bush3;
	public static TextureRegion[] bushes;
	
	// Cloud textures
	public static TextureRegion cloud1;
	public static TextureRegion cloud2;
	public static TextureRegion cloud3;
	public static TextureRegion cloud4;
	public static TextureRegion[] clouds;
	
	// Ground textures
	public static TextureRegion ground_hills;
	public static TextureRegion ground_hills_trans;
	public static TextureRegion ground_forest;
	public static TextureRegion ground_forest_trans;
	public static TextureRegion start_mound;
	
	// Game sounds
	public static Music noodling;
	
	// Font rendering
	public static SpriteBatch chunkBatch;
	public static BitmapFont chunkFont;
	
	// Particles
	public static ParticleEffect partFeathers;
	public static ParticleEffect partDirt;
	public static ParticleEffect partAir;
	
	// NOT IN USE: Dynamic screen scaling for use across multiple devices
	public static float SCREEN_SCALE;

	public static final float DEV_WIDTH = 1024f;
	public static final float DEV_HEIGHT = 768f;
	// What percent of the screen height the cat should take up;
	public static final float PERC_CHARACTER = 0.2f;
	
	
	public static void load () {
	
		// Load assets from file
		atlas = new TextureAtlas("momentum.atlas");
		
		back_gradient_1 = atlas.findRegion("back_gradient");
		back_tile_hills = atlas.findRegion("back_tile");
		back_tile_forest = atlas.findRegion("back_forest");
		back_dist_hills = new TextureRegion(new Texture(Gdx.files.internal("bck_hills.png")));
		back_dist_forest_trees = new TextureRegion(new Texture(Gdx.files.internal("bck_forest.png")));
		back_dist_forest_fartrees = new TextureRegion(new Texture(Gdx.files.internal("bck_forest_2.png")));
		
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
		
		// Load the bushes into an array for random selection
		bushes[0] = bush1;
		bushes[1] = bush2;
		bushes[2] = bush3;
		
		cloud1 = atlas.findRegion("cloud1");
		cloud2 = atlas.findRegion("cloud2");
		cloud3 = atlas.findRegion("cloud3");
		cloud4 = atlas.findRegion("cloud4");
		clouds = new TextureRegion[4];
		
		// Load the clouds into an array for random selection
		clouds[0] = cloud1;
		clouds[1] = cloud2;
		clouds[2] = cloud3;
		clouds[3] = cloud4;
		
		ground_hills = new TextureRegion(new Texture(Gdx.files.internal("gnd_hills.png")));
		ground_hills_trans = new TextureRegion(new Texture(Gdx.files.internal("gnd_hills_trans.png")));
		ground_forest = new TextureRegion(new Texture(Gdx.files.internal("gnd_forest.png")));
		ground_forest_trans = new TextureRegion(new Texture(Gdx.files.internal("gnd_forest_trans.png")));
		start_mound = new TextureRegion(new Texture(Gdx.files.internal("start_mound.png")));
		
		// Create new particle effects with given information
		partFeathers = new ParticleEffect();
		partFeathers.load(Gdx.files.internal("part_feathers"), atlas);
		partDirt = new ParticleEffect();
		partDirt.load(Gdx.files.internal("part_dirt"), atlas);
		partAir = new ParticleEffect();
		partAir.load(Gdx.files.internal("part_air"), atlas);

		// Define the screen scale as a percentage of the character taking up the screen (?)
		SCREEN_SCALE = (PERC_CHARACTER * Gdx.graphics.getHeight()) / ((float)catBody.getRegionHeight());
		
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
