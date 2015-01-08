package com.brassbeluga.momentum;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	public static final float WORLD_WIDTH = 100;
	public static final float WORLD_HEIGHT = 60; 
	
	public Vector2 gravity;
	
	public Spider spider;
	public Array<Peg> pegs;
	
	World() {
		gravity = new Vector2(0.0f, -0.01f);
		spider = new Spider(0f, WORLD_HEIGHT - 10, 10, 10, Assets.spider, this);
		pegs = new Array<Peg>();
		generatePegs(5);
	}
	
	public void update(float delta) {
		spider.update(gravity);
		
		if (spider.x >= WORLD_WIDTH) {
			spider.x = 0;
			spider.y = WORLD_HEIGHT - 10;
			spider.peg = null;
			generatePegs(5);
		} else if (spider.y <= 0) {
			spider.x = 0;
			spider.y = WORLD_HEIGHT - 10;
			spider.velocity = new Vector2(0,0);
			spider.angle = 0;
			spider.angVel = 0;
			spider.peg = null;
		}
	}
	
	/**
	 * Renders the world.
	 * 
	 * @param batch The sprite batch to draw to.
	 */
	public void render(SpriteBatch batch) {
		spider.render(batch);
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
			Peg peg = new Peg(x, y, 0.2f, Assets.peg, this);
			pegs.add(peg);
		}
	}
	
	public void onTouchDown(float x, float y, int pointer, int button) {
		Iterator<Peg> iter = pegs.iterator();
		Peg peg = iter.next();
		Peg closePeg = peg;
		Vector2 touch = new Vector2(x, y);
		Vector2 pegPos = new Vector2(peg.x, peg.y);
		float dist = pegPos.dst(touch);
		// Find the closest peg to touch;
		while (iter.hasNext()) {
			peg = iter.next();
			pegPos.set(peg.x, peg.y);
			float newDist = pegPos.dst(touch);
			if (newDist < dist) {
				dist = newDist;
				closePeg = peg;
			}
		}
		spider.setPeg(closePeg);
		
	}

	public void onTouchUp(float x, float y, int pointer, int button) {
		spider.peg = null;
	}
	
}
