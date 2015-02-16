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
	
	public LevelManager(World world, LevelType startType) {
		this.world = world;
		this.transitionToLevel = null;
		
		backTile = new GameSprite(Assets.back_tile_hills, 0, 0);
		backTile.addAnimation(LevelType.HILLS.toString(), Assets.back_tile_hills);
		backTile.addAnimation(LevelType.FOREST.toString(), Assets.back_tile_forest);
		ground = new GameSprite(Assets.ground_hills, 0, 0);
		ground.addAnimation(LevelType.HILLS.toString(), Assets.ground_hills);
		ground.addAnimation(LevelType.FOREST.toString(), Assets.ground_forest);
		backGround = new GameSprite(Assets.back_dist_hills, 0, 0);
		backGround.drawOrder = 1;
		backGround.addAnimation(LevelType.HILLS.toString(), Assets.back_dist_hills);
		backGround.addAnimation(LevelType.FOREST.toString(), Assets.back_dist_forest_trees);
		transition = new GameSprite(Assets.ground_forest_trans, 0, 0);
		transition.addAnimation(LevelType.HILLS.toString(), Assets.ground_hills_trans);
		transition.addAnimation(LevelType.FOREST.toString(), Assets.ground_forest_trans);
		transition.visible = false;
		ground.addChild(backGround);
		ground.addChild(transition);
		start_mound = new GameSprite(Assets.start_mound, 0, 0);
		
		setLevelType(startType);
	}
	
	public void transitionTo(LevelType type) {
		transitionToLevel = type;
	}
	
	public void setLevelType(LevelType type) {
		this.currentLevelType = type;
		
		backTile.playAnimation(currentLevelType.toString());
		ground.playAnimation(currentLevelType.toString());
		backGround.playAnimation(currentLevelType.toString());
		transition.visible = false;
	}
	
	public void renderLevel(SpriteBatch batch) {
		for (int i = 0; i < world.WORLD_WIDTH / backTile.bounds.x; i++) {
			batch.draw(backTile.texture, i * backTile.bounds.x, 0, backTile.bounds.x, backTile.bounds.y);
		}
		ground.draw(batch);
	}

	
}
