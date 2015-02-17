package com.brassbeluga.momentum;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brassbeluga.momentum.LevelManager.BiomeType;
import com.brassbeluga.momentum.database.MomentumDB;
import com.brassbeluga.momentum.gameobjects.Bird;
import com.brassbeluga.momentum.gameobjects.Player;
import com.brassbeluga.momentum.gui.ProgressBar;
import com.brassbeluga.momentum.gui.Speedometer;

public class World {
	private Momentum game;
	private static final int BIRD_COUNT = 5;
	private static final float INC_FACTOR = 4.0f;
	
	// World dimensions.
	public static final float WORLD_WIDTH = 100;
	public static final float WORLD_HEIGHT = 60; 
	
	// Starting position for the player.
	public static final float PLAYER_START_X = 8;
	public static final float PLAYER_START_Y = WORLD_HEIGHT - 18;
	
	// Velocity at which screen rumbling begins.
	public static final float RUMBLE_VELOCITY = 2.0f;
	
	// World gravity
	public static Vector2 gravity;

	// Kinematic game objects
	public Player player;
	public Array<Bird> birds;
	
	// Which pointer (finger) is currently touching the screen 
	public int currPointer;
	
	// Bounds for bird generation.
	private Rectangle birdBounds;
	private Rectangle easyStartBounds;
	
	// Starting mound for player.
	private GameSprite start_mound;
	
	// Rumbler for the screen.
	private WorldRumbler rumbler;
	
	// Manager for transitions and generation of different biomes
	private LevelManager levelManager;
	
	public Speedometer meter;
	public ProgressBar progress;
	
	// Level progression
	public Array<BiomeType> levels;
	public static final int LEVEL_SET = 8;
	public int level;
	
	public World(Momentum game) {
		this.game = game;
		gravity = new Vector2(0.0f, -0.012f);
		player = new Player(PLAYER_START_X, PLAYER_START_Y, Assets.catBody, this);
		birds = new Array<Bird>();
		
		level = 0;
		levels = new Array<BiomeType>();
		generateLevelSequence();
		
		birdBounds = new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		easyStartBounds = new Rectangle(0, WORLD_HEIGHT / 4.0f, WORLD_WIDTH, ((3.0f / 4.0f) * WORLD_HEIGHT));
		start_mound = new GameSprite(Assets.start_mound, 0, 0);
		
		meter = new Speedometer(WORLD_WIDTH / 3.0f - WORLD_WIDTH / 6.0f, 4.0f);
		meter.centerOrigin();
		progress = new ProgressBar(WORLD_WIDTH -  WORLD_WIDTH / 6.0f, 4.0f);

		rumbler = new WorldRumbler(game.camera);
		levelManager = new LevelManager(this);
		advanceLevel();
		
		// Generate the first level
		newLevel(easyStartBounds);
	}
	
	/*
	 * Loads all biome-types into the level sequence list and shuffles it
	 */
	private void generateLevelSequence() {
		levels.clear();
		for (BiomeType biome : BiomeType.values())
			levels.add(biome);
		levels.shuffle();
		if (levelManager != null)
			levelManager.resetTransitions();
	}

	
	/**
	 * Updates the world and all of the bodies contained within it.
	 * 
	 * @param delta The time thWat has passed since update was last called.
	 */
	public void update(float delta) {
		updateCamera(delta);
		levelManager.update(delta);
		player.update(gravity);
		//Update the ui components
		meter.updateMeter(MathUtils.clamp((Math.abs(player.velocity.len()) / Player.SPEED_THRESHOLD), 0.0f, 1.0f));
		progress.updateProgress(((level % LEVEL_SET) * WORLD_WIDTH + player.x) / (WORLD_WIDTH * LEVEL_SET));
		for (Bird bird : birds) {
			if (!bird.held) {
				if (bird.x - bird.bounds.width > WORLD_WIDTH)
					bird.x = -bird.bounds.width;
			}
			bird.update(gravity);
		}
		if (player.y > WORLD_HEIGHT + player.bounds.height / 2) {
			// Player has hit the ceiling
			player.clearPeg();
			player.velocity.y = -player.velocity.y;
			rumbler.rumble(1.0f, 0.2f, delta);
		}
		if (player.x >= WORLD_WIDTH && !player.dead) {
			// The player has moved to the next screen.
			player.x = 0;
			if (player.y < PLAYER_START_Y) {
				player.y = PLAYER_START_Y;
			}
			player.bird = null;
			player.setTailNormal();
			player.nextScreen();
			level++;
			// Advance the level if a multiple of level set is reached
			if (level % LEVEL_SET == 0 && level != 0)
				advanceLevel();
			else if (level % LEVEL_SET == LEVEL_SET - 2)
				levelManager.startTransition(levels.peek());
			newLevel(birdBounds);
		} else if (player.y < player.bounds.height / 2.0f) {
			player.setDead();
		} else if (player.dead) {
			if (player.velocity.x == 0.0f) {
				// Player has died.
				player.dead = false;
				game.onDeath(level);
				generateLevelSequence();
				if (level > 5) {
					newLevel(birdBounds);
				} else {
					newLevel(easyStartBounds);
				}
				advanceLevel();
				level = 0;
			}
		}
	}
	
	/**
	 * Advances the level in the level manager and
	 * regenerates the level sequence if necessary.
	 * Also updates the endpoint textures on the
	 * progress bar.
	 */
	private void advanceLevel() {
		BiomeType newType = levels.pop();
		levelManager.setLevelType(newType);
		if (levels.size == 0)
			generateLevelSequence();
		progress.setEndpoints(levelManager.getMarker(newType),
				levelManager.getMarker(levels.peek()));
	}
	
	/**
	 * Renders the world.
	 * 
	 * @param batch The sprite batch to draw to.
	 */
	public void render(SpriteBatch batch) {
		levelManager.renderLevel(batch);
		
		// Need a starting mound if the first level.
		if (level == 0)
			batch.draw(start_mound.texture, 0, 0, start_mound.bounds.x, start_mound.bounds.y);
		
		player.render(batch);
		for (Bird bird : birds) {
			bird.render(batch);
		}
		player.postRender(batch);
		
		meter.draw(batch);
		progress.draw(batch);
	}
	
	/**
	 * Should be called from the controller whenever a touch down event occurs.
	 * 
	 * @param x The world x coordinate 
	 * @param y The world y coordinate
	 * @param pointer The pointer (finger)
	 * @param button The button (always 0 for touch devices)
	 */
	public void onTouchDown(float x, float y, int pointer, int button) {
		if (player.bird == null) {
			currPointer = pointer;
			Iterator<Bird> iter = birds.iterator();
			Bird bird = iter.next();
			Bird closePeg = bird;
			Vector2 touch = new Vector2(x, y);
			Vector2 pegPos = new Vector2(bird.x, bird.y);
			float dist = pegPos.dst(touch);
			// Find the closest peg to the touchpoint in the game world.
			while (iter.hasNext()) {
				bird = iter.next();
				pegPos.set(bird.x, bird.y);
				float newDist = pegPos.dst(touch);
				if (newDist < dist) {
					dist = newDist;
					closePeg = bird;
				}
			}
			player.setPeg(closePeg);
		}
	}

	/**
	 * Should be called from the controller whenever a touch up event occurs.
	 * 
	 * @param x The world x coordinate 
	 * @param y The world y coordinate
	 * @param pointer The pointer (finger)
	 * @param button The button (always 0 for touch devices)
	 */
	public void onTouchUp(float x, float y, int pointer, int button) {
		// Ensure that the same pointer that caused the player to be attached
		// to the peg is the one that was released.
		if (pointer == currPointer) {
			player.clearPeg();
		}
	}
	
	private void updateCamera(float delta) {
		float camX = WORLD_WIDTH / 2.0f;
		float camY = WORLD_HEIGHT / 2.0f;
		
		game.camera.position.set(camX, camY, 0.0f);
		
		if (player.velocity.len() > RUMBLE_VELOCITY) {
			rumbler.rumble((float) (0.1f * (Math.pow(player.velocity.x / RUMBLE_VELOCITY, 3f))), 0.05f, delta);
		}
		
		game.camera.update();
	}


	private void newLevel(Rectangle bounds) {
		levelManager.generateLevel();
		birds.clear();
		
		// Generate the birds semi-randomly
		float lastX = 0;
		for (int i = 0; i < BIRD_COUNT; i++) {
			float xInc = bounds.width / INC_FACTOR - bounds.width
					/ (2.0f * INC_FACTOR)
					+ MathUtils.random(0, bounds.width / INC_FACTOR);
			float x = lastX + xInc;
			x = x % bounds.width;
			x += bounds.x;
			lastX = x;
			float y = bounds.y
					+ MathUtils.random(bounds.height / INC_FACTOR,
							bounds.height);
			Bird bird = new Bird(x, y, Assets.birdBody);
			birds.add(bird);
		}
	}
	
	
}
