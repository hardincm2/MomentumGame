package com.brassbeluga.momentum;

import java.util.Iterator;

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
	public static final float PLAYER_START_X = 10;
	public static final float PLAYER_START_Y = WORLD_HEIGHT - 10;

	public Vector2 gravity;
	public Player player;
	public Array<Peg> pegs;
	
	private Rectangle resetPegBounds;

	// Keeps track of which level the player is currently on.
	public int level;
	
	// Which pointer (finger) is currently touching the screen 
	public int currPointer;
	
	World(Momentum game) {
		this.game = game;
		gravity = new Vector2(0.0f, -0.01f);
		player = new Player(PLAYER_START_X, PLAYER_START_Y, Assets.catBody, this);
		pegs = new Array<Peg>();
		level = 0;
		resetPegBounds = new Rectangle(WORLD_WIDTH / 4.0f, 0, WORLD_WIDTH * 3.0f / 4.0f, WORLD_HEIGHT);
		generatePegs(5, 4.0f, resetPegBounds);
	}
	
	/**
	 * Updates the world and all of the bodies contained within it.
	 * 
	 * @param delta The time that has passed since update was last called.
	 */
	public void update(float delta) {
		player.update(gravity);
		if (player.x >= WORLD_WIDTH) {
			// The player has moved to the next screen.
			player.x = 0;
			player.y = PLAYER_START_Y;
			player.peg = null;
			player.setTailNormal();
			level++;
			generatePegs(5);
		} else if (player.y <= 0) {
			// Player has died.
			game.onDeath(level);
			player.reset(PLAYER_START_X, PLAYER_START_Y);
			level = 0;
			generatePegs(5, 4.0f, resetPegBounds);
		}
	}
	
	/**
	 * Renders the world.
	 * 
	 * @param batch The sprite batch to draw to.
	 */
	public void render(SpriteBatch batch) {
		player.render(batch);
		for (Peg peg : pegs) {
			peg.render(batch);
		}
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
		if (player.peg == null) {
			currPointer = pointer;
			Iterator<Peg> iter = pegs.iterator();
			Peg peg = iter.next();
			Peg closePeg = peg;
			Vector2 touch = new Vector2(x, y);
			Vector2 pegPos = new Vector2(peg.x, peg.y);
			float dist = pegPos.dst(touch);
			// Find the closest peg to the touchpoint in the game world.
			while (iter.hasNext()) {
				peg = iter.next();
				pegPos.set(peg.x, peg.y);
				float newDist = pegPos.dst(touch);
				if (newDist < dist) {
					dist = newDist;
					closePeg = peg;
				}
			}
			player.setPeg(closePeg);
		}
		player.setFace(Assets.catFaceBlink);
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
		player.setFace(Assets.catFaceNormal);
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
		pegs.clear();
		float lastX = 0;
		for (int i = 0; i < amount; i++) {
			float xInc = bounds.width / incFactor - bounds.width / (2.0f * incFactor) + MathUtils.random(0, bounds.width / 4);
			float x = lastX + xInc;
			x = x % bounds.width;
			x += bounds.x;
			lastX = x;
			float y = bounds.y + MathUtils.random(bounds.height / incFactor, bounds.height);
			Peg peg = new Peg(x, y, Assets.peg, this);
			pegs.add(peg);
		}
	}
}
