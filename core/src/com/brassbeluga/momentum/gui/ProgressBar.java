package com.brassbeluga.momentum.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;

public class ProgressBar extends GameSprite {

	private GameSprite start;
	private GameSprite end;
	private GameSprite player;
	public static final float WIDTH_PERCENT = 0.80f;
	
	public ProgressBar(float x, float y) {
		super(Assets.progressBar, x, y);
		centerOrigin();
		start = new GameSprite(Assets.markerHills, x - (bounds.x * WIDTH_PERCENT) / 2.0f, 0);
		start.centerOrigin();
		end = new GameSprite(Assets.markerForest, x + (bounds.x * WIDTH_PERCENT) / 2.0f, 0);
		end.centerOrigin();
		player = new GameSprite(Assets.markerPlayer, x - (bounds.x * WIDTH_PERCENT) / 2.0f, 0);
		player.centerOrigin();
		addChild(start);
		addChild(end);
		addChild(player);
	}

}
