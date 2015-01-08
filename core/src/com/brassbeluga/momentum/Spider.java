package com.brassbeluga.momentum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Spider extends GameObject {

	public Peg peg;
	public float angVel;
	public float swingRadius;
	
	public Spider(float x, float y, float width, float height, TextureRegion texture, World world) {
		super(x, y, width, height, texture);
	}
	
	@Override
	public void update(Vector2 gravity) {
		velocity.add(gravity);
		x += velocity.x;
		y += velocity.y;
		if (peg != null) {
			Vector2 pos = getPosition(new Vector2());
			Vector2 pegPos = peg.getPosition(new Vector2());
			Vector2 rope = pegPos.sub(pos);
			rope.rotate90(-1);
			float magnitude = rope.len() * rope.len();
			velocity = rope.scl((rope.dot(velocity) / magnitude));
		}
	}
	
	public void setPeg(Peg peg) {
		this.peg = peg;
		Vector2 pos = getPosition(new Vector2());
		Vector2 pegPos = peg.getPosition(new Vector2());
		swingRadius = pos.dst(pegPos);
		Vector2 rope = pegPos.sub(pos);
		rope.rotate90(-1);
		float magnitude = rope.len() * rope.len();
		velocity = rope.scl((rope.dot(velocity) / magnitude));
	}

}
