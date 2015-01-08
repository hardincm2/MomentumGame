package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	private float accumulator;

	public World world;
	public OrthographicCamera camera;
	private BitmapFont font;
	public SpriteBatch batch;
	public ShapeRenderer shapes;
	
	
	public GameScreen(final Momentum game) {
		world = new World();
		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		accumulator = 0;
		shapes = new ShapeRenderer();
		shapes.setAutoShapeType(true);
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen and set a screen color.
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Fixed time steps for predictable physics.
		accumulator += delta;
		while (accumulator >= STEP) {
			world.update(delta);
			accumulator -= delta;
		}
		
		// Update the camera and sync the batch with the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		
		// Render the game to the spritebatch.
		batch.begin();
		world.render(batch);
		font.setColor(Color.RED);
		font.setScale(.25f, .25f);
		font.draw(batch, "" + (int) world.maxDist, 10, 10);
		batch.end();
		
		if (world.spider != null && world.spider.peg != null) {
			shapes.setColor(Color.PURPLE);
			Gdx.gl20.glLineWidth(5);
			shapes.setProjectionMatrix(camera.combined);
			shapes.begin();
			shapes.line(world.spider.x, world.spider.y, world.spider.peg.x, world.spider.peg.y);
			shapes.end();
		}
		
		
	}
	
	@Override
	public void show() {
		Assets.noodling.setLooping(true);
		Assets.noodling.play();
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
	}
}
