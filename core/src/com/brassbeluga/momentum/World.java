package com.brassbeluga.momentum;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	public GameScreen game;
	
	public static final float WORLD_WIDTH = 100;
	public static final float WORLD_HEIGHT = 60; 
	
	public Vector2 gravity;
	public Player player;
	public Array<Peg> pegs;
	
	public int level;
	
	World(GameScreen game) {
		this.game = game;
		gravity = new Vector2(0.0f, -0.01f);
		player = new Player(0f, WORLD_HEIGHT - 10, Assets.catBody, this);
		player.x += player.bounds.width;
		pegs = new Array<Peg>();
		level = 0;
		generatePegs(5);
	}
	
	public void update(float delta) {
		player.update(gravity);
		if (player.x >= WORLD_WIDTH) {
			player.x = 0;
			player.y = WORLD_HEIGHT - 10;
			player.peg = null;
			level++;
			generatePegs(5);
		} else if (player.y <= 0) {
			game.game.onDeath();
			game.game.death.setLevels(level);
			player.resetSpider(10, WORLD_HEIGHT - 10);
			level = 0;
			generatePegs(5);
			game.startTouch = true;
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
	
	private void generatePegs(int amount) {
		pegs.clear();
		float lastX = 0;
		for (int i = 0; i < amount; i++) {
			float xInc = WORLD_WIDTH / 4 - WORLD_WIDTH / 8 + MathUtils.random(0, WORLD_WIDTH / 4);
			float x = lastX + xInc;
			x = x % WORLD_WIDTH;
			lastX = x;
			float y = MathUtils.random(WORLD_HEIGHT / 4, WORLD_HEIGHT);
			Peg peg = new Peg(x, y, Assets.peg, this);
			pegs.add(peg);
		}
	}
	
	public void onTouchDown(float x, float y, int pointer, int button) {
		if (!game.startTouch && player.peg == null) {
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
		
	}

	public void onTouchUp(float x, float y, int pointer, int button) {
		game.startTouch = false;
		player.clearPeg();
	}
	
}
