package com.brassbeluga.momentum;

import javax.swing.text.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Spider extends GameObject {
	public static float HEIGHT = 10;
	public static float WIDTH = 10;
	public static float TARGET_ANGLE_VEL = 10;
	public static float MAX_ANG_VEL = 5;
	public Vector2 tailOff = new Vector2(-42f, -40f);

	public Peg peg; // null if spider is currently not attached
	public float pegAngle;
	public float angVel = 1;
	public float swingRadius;
	public float lastAngle;
	public float targetAngle;
	
	public Vector2 lastPos;
	public Vector2 pos;
	
	public TailObject tail;
	
	public Spider(float x, float y, TextureRegion texture, World world) {
		super(x, y,texture);
		targetAngle = angle;
		lastAngle = angle;
		pegAngle = angle;
		tailOff.set((tailOff.x / texture.getRegionWidth()) * rect.width, (tailOff.y / texture.getRegionHeight()) * rect.height);
		float tailX = x + tailOff.x;
		float tailY = y + tailOff.y;
		tail = new TailObject(this, tailX, tailY, tailOff.x, tailOff.y, Assets.catTail);
		lastPos = new Vector2(x, y);
		pos = new Vector2(x, y);
	}
	
	@Override
	public void update(Vector2 gravity) {
		velocity.add(gravity);
		x += velocity.x;
		y += velocity.y;
		if (peg != null) {
			Vector2 pos = new Vector2();
			Vector2 pegPos = new Vector2();
			Vector2 newPos = getPosition(pos);
			pegPos = peg.getPosition(pegPos);
			Vector2 rope = newPos.sub(pegPos).setLength(swingRadius);
			pegPos.add(rope);
			x = pegPos.x;
			y = pegPos.y;
			
			pos = getPosition(new Vector2());
			pegPos = peg.getPosition(new Vector2());
			rope = pegPos.sub(pos);
			
			rope.setLength(velocity.len());
			pegAngle = rope.angle();
			rope.rotate90(-1);
			lastAngle = angle;
			targetAngle = rope.angle() - 90;
			float magnitude = rope.len() * rope.len();
			velocity = rope.scl((rope.dot(velocity) / magnitude));
			float diff = ((((targetAngle - angle) % 360f) + 540f) % 360f) - 180f;
			float dir = Math.signum(diff);
			if (Math.abs(diff) <= TARGET_ANGLE_VEL)
				angle = targetAngle;
			else
				angle += dir * TARGET_ANGLE_VEL;
		}else{
			angle += angVel;
			/*
			pos.set(x,y);
			angle = (float) (MathUtils.radiansToDegrees * Math.atan2(pos.y - lastPos.y, pos.x - lastPos.x)) - 90;
			lastPos.set(x, y);
			*/
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		tail.render(batch);
		batch.draw(region, x + rect.x, y + rect.y, halfWidth, halfHeight, 
				rect.width, rect.height, 1.0f, 1.0f, angle);
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
		angVel = 0;
	}
	
	public void clearPeg() {
		peg = null;
		targetAngle = 0;
		angVel = MathUtils.clamp(angle - lastAngle, -MAX_ANG_VEL, MAX_ANG_VEL);
	}
	
	public void resetSpider(float x, float y) {
		this.x = x;
		this.y = y;
		this.velocity.x = 0;
		this.velocity.y = 0;
		this.peg = null;
		this.angVel = 0;
		this.angle = 0;
		this.lastAngle = 0;
	}

}
