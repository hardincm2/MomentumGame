package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {
	private static final float STEP = 1/60f;
	private float accumulator;

	public World world;
	public OrthographicCamera camera;
	private BitmapFont font;
	public SpriteBatch batch;
	public ShapeRenderer shapes;
	public Vector3 cameraPos;
	
	public Momentum game;
	
	public boolean startTouch;
	
	
	public GameScreen(final Momentum game) {
		this.game = game;
		world = new World(this);
		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		accumulator = 0;
		shapes = new ShapeRenderer();
		shapes.setAutoShapeType(true);
		cameraPos = new Vector3(0, 0, 0);
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
		Assets.chunkFont.setColor(Color.PURPLE);
		Assets.drawText("" + world.level, 30, 30, BitmapFont.HAlignment.LEFT);
		Assets.chunkFont.setColor(Color.WHITE);
		Assets.chunkBatch.end();
		
		if (world.spider != null && world.spider.peg != null) {
			shapes.setColor(Color.PURPLE);
			Gdx.gl20.glLineWidth(5);
			shapes.setProjectionMatrix(camera.combined);
			shapes.begin();
			shapes.line(world.spider.x, world.spider.y, world.spider.peg.x, world.spider.peg.y);
			shapes.end();
		}
		
		// Fixed time steps for predictable physics.
		accumulator += delta;
		while (accumulator >= STEP) {
			world.update(delta);
			accumulator -= delta;
		}
	}
	
	@Override
	public void show() {
		Assets.noodling.setLooping(true);
		//Assets.noodling.play();
		Gdx.input.setInputProcessor(new GameInputProcessor(world, camera));
		startTouch = Gdx.input.isTouched();
	}
}
