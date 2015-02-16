package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;

public class ForestBiome extends Biome {
	
	public ForestBiome() {
		this.ground = new GameSprite(Assets.ground_forest, 0, 0);
		this.backTile =  new GameSprite(Assets.back_tile_forest, 0, 0);
		this.backGround = new GameSprite(Assets.back_dist_forest_trees, 0, 0);
		this.marker = Assets.markerForest;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
}
