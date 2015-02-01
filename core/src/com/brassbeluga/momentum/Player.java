package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
	public static float HEIGHT = 10;
	public static float WIDTH = 10;
	public static float TARGET_ANGLE_VEL = 10;
	public static float MAX_ANG_VEL = 5;
	public Vector2 tailOff = new Vector2(-38, -38);

	public Peg peg; // null if currently not attached
	public float pegAngle;
	public float angVel = 1;
	public float swingRadius;
	public float lastAngle;
	public float targetAngle;
	
	public float angle;
	
	public Vector2 lastPos;
	public Vector2 pos;
	
	private GameSprite tail;
	private static float TAIL_SPEED = 4;
	private float tailTarget = 0;
	
	public Player(float x, float y, TextureRegion texture, World world) {
		super(x, y,texture);
		angle = 0.0f;
		targetAngle = angle;
		lastAngle = angle;
		pegAngle = angle;
		
		tailOff.set((tailOff.x / texture.getRegionWidth()) * bounds.width, (tailOff.y / texture.getRegionHeight()) * bounds.height);
		//tail = new TailObject(this, tailX, tailY, tailOff.x, tailOff.y, Assets.catTail);
		
		lastPos = new Vector2(x, y);
		pos = new Vector2(x, y);
		
		tail = new GameSprite(Assets.catTail, tailOff.x, tailOff.y);
		Gdx.app.log("", "X: " + tail.bounds.x + " Y: " + tail.bounds.y);
		tail.setOffset(118, 10);
		sprite.children.add(tail);
	}
	
	@Override
	public void update(Vector2 gravity) {
		velocity.add(gravity);
		x += velocity.x;
		y += velocity.y;
		
		if (peg != null) {
			Vector2 pegPos = new Vector2(peg.x, peg.y);
			Vector2 newPos = new Vector2(x, y);
			pegPos.set(peg.x, peg.y);
			Vector2 rope = newPos.sub(pegPos).setLength(swingRadius);
			pegPos.add(rope);
			x = pegPos.x;
			y = pegPos.y;
			
			pos.set(x, y);
			pegPos.set(peg.x, peg.y);
			rope = pegPos.sub(pos);
			
			rope.setLength(velocity.len());
			pegAngle = rope.angle();
			rope.rotate90(-1);
			targetAngle = rope.angle() - 90;
			float magnitude = rope.len() * rope.len();
			velocity = rope.scl((rope.dot(velocity) / magnitude));
			float diff = angleDiff(targetAngle, angle);
			float dir = Math.signum(diff);
			lastAngle = angle;
			if (Math.abs(diff) <= TARGET_ANGLE_VEL)
				angle = targetAngle;
			else
				angle += dir * TARGET_ANGLE_VEL;
			angVel = MathUtils.clamp(angle - lastAngle, -MAX_ANG_VEL, MAX_ANG_VEL);
		}else{
			pos.set(x,y);
			angle += angVel;
			/*
			angle = (float) (MathUtils.radiansToDegrees * Math.atan2(pos.y - lastPos.y, pos.x - lastPos.x)) - 90;
			lastPos.set(x, y);
			*/
		}
		tailTarget = 30 * angVel / MAX_ANG_VEL;
		float diff = angleDiff(tailTarget, tail.angle);
		float dir = Math.signum(diff);
		if (Math.abs(diff) <= TAIL_SPEED)
			tail.angle = tailTarget;
		else
			tail.angle += dir * TAIL_SPEED;
	}
	
	private float angleDiff(float a1, float a2) {
		return ((((a1 - a2) % 360f) + 540f) % 360f) - 180f;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprite.position.set(x, y);
		sprite.angle = angle;
		sprite.draw(batch);
	}
	
	public void setPeg(Peg peg) {
		this.peg = peg;
		Vector2 pos = new Vector2(x, y);
		Vector2 pegPos = new Vector2(peg.x, peg.y);
		swingRadius = pos.dst(pegPos);
		Vector2 rope = pegPos.sub(pos);
		rope.rotate90(-1);
		float magnitude = rope.len() * rope.len();
		velocity = rope.scl((rope.dot(velocity) / magnitude));
	}
	
	public void clearPeg() {
		peg = null;
		targetAngle = 0;
	}
	
	public void reset(float x, float y) {
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
