package com.brassbeluga.momentum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	
	private float accumulator;
	
	//private Color cTop = new Color(77f / 255f, 236f / 255f, 178f / 255f, 1);
	
	private SpriteBatch batch;
	private World world;
	private ShapeRenderer shapeRenderer;
	private Array<GameObject> hitboxes;
	private OrthographicCamera camera;
	
	private List<Music> songs;
	private Music currentSong;
	
	/*
	 * Constructs a new game screen object. Instantiates the game world
	 * and the shape renderer for debugging.
	 */
	public GameScreen(final Momentum game) {
		this.batch = game.batch;
		this.camera = game.camera;
		hitboxes = new Array<GameObject>();
		
		//
		songs = new ArrayList<Music>();
		songs.add(Assets.dragonroost);
		songs.add(Assets.medieval);
		songs.add(Assets.noodling);
		songs.add(Assets.zeldashop);
		
		world = new World(game);
		accumulator = 0;
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen and set a screen color.
		Color worldColor = world.getScreenColor();
		Gdx.gl.glClearColor(worldColor.r, worldColor.g, worldColor.b, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera and sync the batch with the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Render the game to the spritebatch.
		batch.begin();
		world.render(batch);
		batch.end();
		
		Assets.chunkBatch.begin();
		Assets.chunkFont.setColor(Color.DARK_GRAY);
		Assets.drawText(Assets.chunkBatch, "" + world.level, 30, 30);
		Assets.drawText(Assets.chunkBatch, "" + Gdx.graphics.getFramesPerSecond(), 100, 60 );
		Assets.chunkFont.setColor(Color.WHITE);
		Assets.chunkBatch.end();
		
		if (hitboxes.size > 0) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			for (GameObject obj : hitboxes) {
				shapeRenderer.begin(ShapeType.Line);
				shapeRenderer.setColor(Color.RED);
				shapeRenderer.rect(obj.x - obj.bounds.width / 2, obj.y - obj.bounds.height / 2, 
						obj.bounds.width, obj.bounds.height);
				shapeRenderer.end();
			}
		}
		
		// Fixed time steps for predictable physics.
		accumulator += delta;
		while (accumulator >= STEP) {
			world.update(delta);
			accumulator -= delta;
		}
	}
	
	/**
	 * Adds a game object to the list of hitboxes to be drawn
	 * @param obj The game object whose bounds are to be drawn
	 */
	public void drawHitBox(GameObject obj) {
		if (!hitboxes.contains(obj, false))
			hitboxes.add(obj);
	}
	
	/**
	 * Removes a game object from the list of hitboxes to be drawn
	 * @param obj The game object to remove
	 */
	public void unDrawHitBox(GameObject obj) {
		hitboxes.removeValue(obj, false);
	}
	
	@Override
	public void show() {
		Random r = new Random();
		currentSong = songs.get(r.nextInt(songs.size()));
		currentSong.setLooping(true);
		currentSong.play();
		
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
	}
	
	@Override
	public void hide() {
		currentSong.stop();
		Gdx.input.setInputProcessor(null);
	}
}
