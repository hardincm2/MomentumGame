package com.brassbeluga.momentum;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	private Momentum game;
	
	// World dimensions.
	public static final float WORLD_WIDTH = 100;
	public static final float WORLD_HEIGHT = 60; 
	
	// Starting position for the player.
	public static final float PLAYER_START_X = 8;
	public static final float PLAYER_START_Y = WORLD_HEIGHT - 18;
	
	public static final float COLOR_DROPOFF = 20f;
	private Color screenColor;
	
	private GameSprite backTile;
	private GameSprite ground;
	private GameSprite start_mound;

	public Vector2 gravity;
	public Player player;
	public Array<Bird> birds;
	public Array<GameObject> bushes;
	
	private Rectangle resetPegBounds;
	// Keeps track of which level the player is currently on.
	public int level;
	
	// Which pointer (finger) is currently touching the screen 
	public int currPointer;
	
	private Random random;
	private float rumbleX;
	private float rumbleY;
	private float rumbleTime = 0;
	private float currentRumbleTime = 1;
	private float rumblePower = 0;
	private float currentRumblePower = 0;

	private Rectangle easyStartBounds;
	
	World(Momentum game) {
		this.game = game;
		random = new Random();
		gravity = new Vector2(0.0f, -0.012f);
		player = new Player(PLAYER_START_X, PLAYER_START_Y, Assets.catBody, this);
		birds = new Array<Bird>();
		bushes = new Array<GameObject>();
		level = 0;
		resetPegBounds = new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		easyStartBounds = new Rectangle(0, WORLD_HEIGHT / 4.0f, WORLD_WIDTH, ((3.0f / 4.0f) * WORLD_HEIGHT));
		backTile = new GameSprite(Assets.back_tile, 0, 0);
		ground = new GameSprite(Assets.ground, 0, 0);
		start_mound = new GameSprite(Assets.start_mound, 0, 0);
		generatePegs(5, 4.0f, easyStartBounds);
		screenColor = new Color(104f / 255f, 194f / 255f, 219f / 255f, 1.0f);
	}
	
	public Color getScreenColor() {
		float colorSub = 0f;
		if (player.y > WORLD_HEIGHT * 2.0f)
			colorSub = 60f * (player.y - World.WORLD_HEIGHT * 2.0f) / WORLD_HEIGHT;
		return screenColor.set((104f - colorSub) / 255f, (194f - colorSub) / 255f, (219f - colorSub) / 255f, 1.0f);
	}
	
	/**
	 * Updates the world and all of the bodies contained within it.
	 * 
	 * @param delta The time that has passed since update was last called.
	 */
	public void update(float delta) {
		updateCamera(delta);
		player.update(gravity);
		for (Bird bird : birds) {
			if (!bird.held) {
				if (bird.x - bird.bounds.width > WORLD_WIDTH)
					bird.x = -bird.bounds.width;
				if (player.velocity.y > 0 && bird.y < game.camera.position.y - WORLD_HEIGHT / 2)
					bird.y = game.camera.position.y + (WORLD_HEIGHT / 2) + bird.bounds.y;
				if (player.velocity.y < 0 && bird.y > game.camera.position.y + WORLD_HEIGHT / 2)
					bird.y = game.camera.position.y - WORLD_HEIGHT / 2 - MathUtils.random(WORLD_HEIGHT) - bird.bounds.y;
			}
			bird.update(gravity);
		}
		if (player.x >= WORLD_WIDTH && !player.dead) {
			// The player has moved to the next screen.
			player.x = 0;
			if (player.y < PLAYER_START_Y)
				player.y = PLAYER_START_Y;
			player.bird = null;
			player.setTailNormal();
			player.nextScreen();
			level++;
			generatePegs(5);
		}else if (player.y < player.bounds.height / 2.0f) {
			player.setDead();
		}else if (player.dead) {
			if (player.velocity.x == 0.0f) {
				// Player has died.
				player.dead = false;
				if (level > 5) {
					game.onDeath(level);
					generatePegs(5, 4.0f, resetPegBounds);
				}else{
					generatePegs(5, 4.0f, easyStartBounds);
				}
				level = 0;
				
			}
		}
		
	}
	
	public void rumble(float power, float time) {
		rumblePower = power;
		rumbleTime = time;
		currentRumbleTime = 0;
	}
	
	/**
	 * Renders the world.
	 * 
	 * @param batch The sprite batch to draw to.
	 */
	public void render(SpriteBatch batch) {
		for (int i = 0; i < WORLD_WIDTH / backTile.bounds.x; i++)
			batch.draw(backTile.texture, i * backTile.bounds.x, 0, backTile.bounds.x, backTile.bounds.y);
		batch.draw(ground.texture, 0, 0, ground.bounds.x, ground.bounds.y);
		if (level == 0)
			batch.draw(start_mound.texture, 0, 0, start_mound.bounds.x, start_mound.bounds.y);
		for (GameObject bush : bushes)
			bush.render(batch);
		player.render(batch);
		for (Bird bird : birds) {
			bird.render(batch);
		}
		player.postRender(batch);
	}
	
	private void updateCamera(float delta) {
		float camX = WORLD_WIDTH / 2.0f;
		float camY = WORLD_HEIGHT / 2.0f;
		
		/*
		if (player.x > camX)
			camX = player.x;
		*/
		if ((player.y + player.bounds.height / 2.0f) > WORLD_HEIGHT)
			camY = player.y + player.bounds.height / 2.0f - WORLD_HEIGHT / 2.0f;
		
		
		game.camera.position.set(camX, camY, 0.0f);
		if (player.velocity.x > 2.0f)
			rumble((float) (0.1f * (Math.pow(player.velocity.x / 2.0f, 3f))), 0.05f);
		
		if(currentRumbleTime <= rumbleTime) {
			currentRumblePower = rumblePower * ((rumbleTime - currentRumbleTime) / rumbleTime);
			rumbleX = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
			rumbleY = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
		        	              
			game.camera.translate(-rumbleX, -rumbleY);
			currentRumbleTime += delta;
		} else {
			game.camera.position.x = camX;
			game.camera.position.y = camY;
		}
		game.camera.update();
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

	/**
	 * Semi-randomly generates the pegs for the level.
	 * 
	 * @param amount The number of pegs to generate
	 */
	private void generatePegs(int amount) {
		generatePegs(amount, 4.0f, new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT));
	}
	
	private void generatePegs(int amount, float incFactor, Rectangle bounds) {
		birds.clear();
		bushes.clear();
		for (int i = 0; i < Math.round(Math.random() * 8.0); i++) {
			GameObject bush = new GameObject((float) (Math.random() * WORLD_WIDTH), 
					(float) (8 + Math.random() * 4.0f), Assets.bushes[MathUtils.random(2)]);
			bush.sprite.angle = MathUtils.random(20) - 10f;
			bush.sprite.scale.x = bush.sprite.scale.y = MathUtils.random(0.2f) - 0.1f + 1.0f;
			bushes.add(bush);
		}
		float lastX = 0;
		for (int i = 0; i < amount; i++) {
			float xInc = bounds.width / incFactor - bounds.width / (2.0f * incFactor) + MathUtils.random(0, bounds.width / incFactor);
			float x = lastX + xInc;
			x = x % bounds.width;
			x += bounds.x;
			lastX = x;
			float y = bounds.y + MathUtils.random(bounds.height / incFactor, bounds.height);
			Bird bird = new Bird(x, y, Assets.birdBody);
			birds.add(bird);
		}
	}
	
	
}
