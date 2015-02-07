package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	private float accumulator;
	
	private SpriteBatch batch;
	private World world;
	private ShapeRenderer shapeRenderer;
	private Array<GameObject> hitboxes;
	
	private OrthographicCamera camera;
	
	
	public GameScreen(final Momentum game) {
		this.batch = game.batch;
		this.camera = game.camera;
		hitboxes = new Array<GameObject>();
		
		world = new World(game);
		accumulator = 0;
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen and set a screen color.
		Gdx.gl.glClearColor(200 / 255f, 257 / 255f, 240 / 255f, 1f);
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
		Assets.drawText(Assets.chunkBatch, "" + world.player.velocity.x, 100, 60 );
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
	
	public void drawHitBox(GameObject obj) {
		if (!hitboxes.contains(obj, false))
			hitboxes.add(obj);
	}
	
	public void unDrawHitBox(GameObject obj) {
		hitboxes.removeValue(obj, false);
	}
	
	@Override
	public void show() {
		//Assets.noodling.setLooping(true);
		///Assets.noodling.play();
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
	}
	
	@Override
	public void hide() {
		Assets.noodling.stop();
		Gdx.input.setInputProcessor(null);
	}
}
