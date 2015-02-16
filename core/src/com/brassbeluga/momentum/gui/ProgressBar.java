package com.brassbeluga.momentum.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.LevelManager.BiomeType;

public class ProgressBar extends GameSprite {

	private GameSprite start;
	private GameSprite end;
	private GameSprite player;
	public static final float WIDTH_PERCENT = 0.74f;
	public static final float MARK_HEIGHT = 1.5f;
	
	public ProgressBar(float x, float y) {
		super(Assets.progressBar, x, y);
		centerOrigin();
		drawOrder = -1;
		start = new GameSprite(Assets.markerHills, -(bounds.x * WIDTH_PERCENT) / 2.0f, MARK_HEIGHT);
		start.centerOrigin();
		end = new GameSprite(Assets.markerForest, (bounds.x * WIDTH_PERCENT) / 2.0f, MARK_HEIGHT);
		end.centerOrigin();
		player = new GameSprite(Assets.markerPlayer, -(bounds.x * WIDTH_PERCENT) / 2.0f, MARK_HEIGHT);
		player.centerOrigin();
		addChild(start);
		addChild(end);
		addChild(player);
	}
	
	public void updateProgress(float playerProgress) {
		player.position.x = (float) (-(bounds.x * WIDTH_PERCENT) / 2.0 + (bounds.x * WIDTH_PERCENT * playerProgress));
	}
	
	public void setEndpoints(TextureRegion startTexture, TextureRegion endTexture) {
		start.texture = startTexture;
		end.texture = endTexture;
	}

}
