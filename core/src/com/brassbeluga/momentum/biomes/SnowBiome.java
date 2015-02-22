package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;

public class SnowBiome extends Biome {
	
	private GameSprite backTrees;
	private GameSprite snowBank;
	
	public SnowBiome(World world) {
		this.world = world;
		this.ground = new GameSprite(Assets.ground_snow, PADDING_OFFSET_Y, PADDING_OFFSET_Y);
		this.backTile =  new GameSprite(Assets.back_tile_snow, PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.backGround = new GameSprite(Assets.back_dist_snow_trees, PADDING_OFFSET_X, PADDING_OFFSET_Y);
		backTrees = new GameSprite(Assets.back_dist_snow_mountains, 0, 0);
		backTrees.drawOrder = 1;
		backGround.addChild(backTrees);
		snowBank = new GameSprite(Assets.back_dist_snow_bank, 0, 0);
		snowBank.drawOrder = 1;
		ground.addChild(snowBank);
		this.groundTrans = new GameSprite(Assets.ground_snow_trans, PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.backTrans = new GameSprite(Assets.back_dist_snow_trans, PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.startMound = new GameSprite(Assets.mound_snow, 0, 0);
		this.marker = Assets.markerSnow;
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
