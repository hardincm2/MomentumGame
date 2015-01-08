package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Spider extends GameObject {

	public Peg peg;
	public float angVel;
	public float swingRadius;
	
	public Spider(float x, float y, float width, float height, TextureRegion texture, World world) {
		super(x, y, width, height, texture, world);
	}
	
	@Override
	public void update() {
		velocity.y -= world.gravity;
		rect.x += velocity.x;
		rect.y += velocity.y;
		if (peg != null) {
			rect.x = peg.rect.x;
			rect.y = peg.rect.y;
			velocity.y = 0;
		}
	}
	
	public void setPeg(Peg peg) {
		this.peg = peg;
		Vector2 pos = rect.getPosition(new Vector2());
		Vector2 pegPos = peg.rect.getPosition(new Vector2());
		this.swingRadius = pos.dst(pegPos);
	}

}
