package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class World {
	public static final float WORLD_WIDTH = 100;
	public static final float WORLD_HEIGHT = 60; 
	
	public Spider spider;
	public Array<Peg> pegs;
	
	World() {
		spider = new Spider(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 10, 10, Assets.spider);
	}
	
	public void update(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		spider.render(batch);
		for (Peg peg : pegs) {
			peg.render(batch);
		}
	}
}
