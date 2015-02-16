package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LevelManager {
	private LevelType currentLevelType;
	private LevelType transitionToLevel;
	
	private GameSprite backTile;
	private GameSprite backGround;
	private GameSprite transition;
	private GameSprite ground;
	private GameSprite start_mound;
	
	private SpriteBatch batch;
	private World world;
	
	/** Different level stylings for the game environment */
	public static enum LevelType {
		HILLS, FOREST
	}
	
	public LevelManager(SpriteBatch batch, World world, LevelType startType) {
		this.batch = batch;
		this.world = world;
		this.currentLevelType = startType;
		this.transitionToLevel = null;
		
		backTile = new GameSprite(Assets.back_tile_hills, 0, 0);
		backTile.addAnimation("hills", Assets.back_tile_hills);
		backTile.addAnimation("forest", Assets.back_tile_forest);
		ground = new GameSprite(Assets.ground_hills, 0, 0);
		ground.addAnimation("hills", Assets.ground_hills);
		ground.addAnimation("forest", Assets.ground_forest);
		backGround = new GameSprite(Assets.back_dist_hills, 0, 0);
		backGround.drawOrder = 1;
		backGround.addAnimation("hills", Assets.back_dist_hills);
		backGround.addAnimation("forest", Assets.back_dist_forest_trees);
		transition = new GameSprite(Assets.ground_forest_trans, 0, 0);
		transition.addAnimation("hills", Assets.ground_hills_trans);
		transition.addAnimation("forest", Assets.ground_forest_trans);
		transition.visible = false;
		ground.addChild(backGround);
		ground.addChild(transition);
		start_mound = new GameSprite(Assets.start_mound, 0, 0);
		
		backTile.playAnimation(currentLevelType);
		ground.playAnimation(currentLevelType);
		backGround.playAnimation(currentLevelType);
		transition.visible = false;
	}
	
	public void transitionTo(LevelType type) {
		transitionToLevel = type;
	}
	
	public void renderLevel(SpriteBatch batch) {
		for (int i = 0; i < world.WORLD_WIDTH / backTile.bounds.x; i++) {
			batch.draw(backTile.texture, i * backTile.bounds.x, 0, backTile.bounds.x, backTile.bounds.y);
		}
		ground.draw(batch);
	}

	
}
