package com.brassbeluga.momentum.biomes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.BackgroundSprite;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;

public class SnowBiome extends Biome {

	private BackgroundSprite backTrees;
	private BackgroundSprite snowBank;

	public SnowBiome(World world) {
		this.world = world;
		this.ground = new BackgroundSprite(Assets.ground_snow,
				PADDING_OFFSET_Y, PADDING_OFFSET_Y);
		this.backTile = new BackgroundSprite(Assets.back_tile_snow,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.backGround = new BackgroundSprite(Assets.back_dist_snow_trees,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		backTrees = new BackgroundSprite(Assets.back_dist_snow_mountains, 0, 0);
		backTrees.drawOrder = 1;
		backGround.addChild(backTrees);
		snowBank = new BackgroundSprite(Assets.back_dist_snow_bank, 0, 0);
		snowBank.drawOrder = 1;
		ground.addChild(snowBank);
		this.groundTrans = new BackgroundSprite(Assets.ground_snow_trans,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		groundTrans.xWrap = false;
		this.backTrans = new BackgroundSprite(Assets.back_dist_snow_trans,
				PADDING_OFFSET_X, PADDING_OFFSET_Y);
		this.startMound = new GameSprite(Assets.mound_snow, 0, 0);
		ground.addChild(startMound);
		this.marker = Assets.markerSnow;
	}

	@Override
	public void draw(SpriteBatch batch) {
		backTrees.scrollSpeed = -world.player.velocity.x / 27.0f;
		snowBank.scrollSpeed = - world.player.velocity.x / 26.0f;
		super.draw(batch);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

}
