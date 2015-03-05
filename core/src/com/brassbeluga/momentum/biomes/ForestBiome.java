package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.BackgroundSprite;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;

public class ForestBiome extends Biome {

	private BackgroundSprite backTrees;

	public ForestBiome(World world) {
		this.world = world;
		this.ground = new BackgroundSprite(Assets.ground_forest,
				PADDING_OFFSET_Y, PADDING_OFFSET_Y);
		this.backTile = new BackgroundSprite(Assets.back_tile_forest,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.backGround = new BackgroundSprite(Assets.back_dist_forest_trees,
				PADDING_OFFSET_X, -2 * PADDING_OFFSET_Y);
		backTrees = new BackgroundSprite(Assets.back_dist_forest_fartrees, 0, 0);
		backTrees.drawOrder = 1;
		backGround.addChild(backTrees);
		this.groundTrans = new BackgroundSprite(Assets.ground_forest_trans,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.backTrans = new BackgroundSprite(Assets.back_dist_forest_trans,
				PADDING_OFFSET_X, -2 * PADDING_OFFSET_Y);
		this.startMound = new GameSprite(Assets.mound_forest, 0, 0);
		ground.addChild(startMound);
		this.marker = Assets.markerForest;
	}

	@Override
	public void draw(SpriteBatch batch) {
		backTrees.scrollSpeed = -world.player.velocity.x / 37.0f;
		super.draw(batch);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}
}
