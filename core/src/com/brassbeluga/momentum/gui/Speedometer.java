package com.brassbeluga.momentum.gui;

import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;

public class Speedometer extends GameSprite {
	
	// What percent of the sprite bounds should the meter filling stretch across
	public static final float WIDTH_PERCENT = 0.80f;
	public static final float BAR_ACCEL = 0.01f;
	private float curPercent = 0.0f;
	private GameSprite fill;

	public Speedometer(float x, float y) {
		super(Assets.meterOverlay, x, y);
		centerOrigin();
		fill = new GameSprite(Assets.meterFill, 0, 0);
		fill.drawOrder = 1;
		fill.centerOrigin();
		fill.position.x = -(bounds.x * WIDTH_PERCENT) / 2.0f + 0.5f;
		addChild(fill);
	}

	public void updateMeter(float speedPercent) {
		if (Math.abs(speedPercent - curPercent) > BAR_ACCEL) {
			curPercent += BAR_ACCEL * Math.signum(speedPercent - curPercent);
		}else{
			curPercent = speedPercent;
		}
		fill.bounds.x = bounds.x * WIDTH_PERCENT * curPercent;
	}

}
