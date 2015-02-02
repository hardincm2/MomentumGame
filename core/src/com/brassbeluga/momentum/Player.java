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
	public static float ANG_DECAY = 0.995f;
	public Vector2 tailOff = new Vector2(-43, -43);

	public Peg peg; // null if currently not attached
	public float pegAngle;
	public float angVel = 0;
	public float swingRadius;
	public float lastAngle;
	public float targetAngle;
	
	public float angle;
	
	public Vector2 lastPos;
	public Vector2 pos;
	
	private GameSprite tail;
	private GameSprite tailLong;
	private GameSprite tailPeg;
	private GameSprite face;
	private static float TAIL_SPEED = 1;
	private static float TAIL_ANGLE_MAX = 60;
	private float tailTarget = 0;
	private float waveDelta = 0;
	private float baseHeight;
	private static float BREATH_FACTOR = 25f;
	
	public boolean started;
	
	public Player(float x, float y, TextureRegion texture, World world) {
		super(x, y,texture);
		angle = 0.0f;
		targetAngle = angle;
		lastAngle = angle;
		pegAngle = angle;
		started = false;
		
		baseHeight = sprite.bounds.y;
		
		tailOff.set((tailOff.x / texture.getRegionWidth()) * bounds.width, (tailOff.y / texture.getRegionHeight()) * bounds.height);

		lastPos = new Vector2(x, y);
		pos = new Vector2(x, y);
		
		face = new GameSprite(Assets.catFaceNormal, 0, 0);
		face.centerOrigin();
		face.addAnimation("blink", Assets.catFaceBlink, 5.0f, Assets.catFaceNormal, 5.0f);
		face.addAnimation("eyeclose", Assets.catFaceBlink);
		face.addAnimation("normal", Assets.catFaceNormal);
		
		tail = new GameSprite(Assets.catTail, tailOff.x, tailOff.y);
		tail.setOffset(60, 10);
		tail.drawOrder = 1;
		
		GameSprite rings = new GameSprite(Assets.catTailRings, 0.0f, 0.0f);
		rings.setOffset(60, 10);
		rings.visible = false;
		tail.addChild(rings);
		
		tailLong = new GameSprite(Assets.catTailLong, tailOff.x + 0.2f, tailOff.y + 0.1f);
		tailLong.setOffset(10, 12);
		tailLong.setVisible(false);
		tailLong.drawOrder = 1;

		
		tailPeg = new GameSprite(Assets.catTailCurl, 0, 0);
		tailPeg.setVisible(false);
		tailPeg.setOffset(33, 34);
		
		sprite.addChild(face);
		sprite.addChild(tail);
		sprite.addChild(tailLong);
	}
	
	@Override
	public void update(Vector2 gravity) {
		if (started) {
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
				angVel *= ANG_DECAY;
				/*
				angle = (float) (MathUtils.radiansToDegrees * Math.atan2(pos.y - lastPos.y, pos.x - lastPos.x)) - 90;
				lastPos.set(x, y);
				*/
			}
			tail.angle = MathUtils.clamp(tail.angle - angVel, -TAIL_ANGLE_MAX / 6f, 60);
			
			float diff = angleDiff(0, tail.angle);
			float dir = Math.signum(diff);
			if (Math.abs(diff) <= TAIL_SPEED)
				tail.angle = tailTarget;
			else
				tail.angle += dir * TAIL_SPEED;
		}else{
			waveDelta += Math.PI / 100;
			tail.angle = (float) (Math.sin(waveDelta) * 20f);
			sprite.bounds.y = (float) (baseHeight + baseHeight / BREATH_FACTOR
					+ (baseHeight / BREATH_FACTOR) * Math.sin(waveDelta));
			face.bounds.y = sprite.bounds.y;
		}
		if (Math.random() < 0.01) {
			face.playAnimation("blink");
		}
	}
	
	private float angleDiff(float a1, float a2) {
		return ((((a1 - a2) % 360f) + 540f) % 360f) - 180f;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// Angling, scaling, and positioning the tail to the peg
		if (peg != null) {
			Vector2 tailPos = new Vector2(tailOff.x, tailOff.y).rotate(angle);
			tailPos.set(tailPos.x + x, tailPos.y + y);
			Vector2 pegTail = new Vector2(peg.x - tailPos.x, peg.y - tailPos.y);
			tailLong.hasAbsoluteAngle = true;
			tailLong.angle = pegTail.angle();
			tailLong.bounds.x = pegTail.len();
			tailPeg.position.set(peg.x, peg.y);
			tailPeg.angle = pegTail.angle() - 140;
		}
		sprite.position.set(x, y);
		sprite.angle = angle;
		sprite.draw(batch);
		tailPeg.draw(batch);
	}
	
	public void setPeg(Peg peg) {
		face.playAnimation("eyeclose");
		if (started) {
			this.peg = peg;
			Vector2 pos = new Vector2(x, y);
			Vector2 pegPos = new Vector2(peg.x, peg.y);
			swingRadius = pos.dst(pegPos);
			Vector2 rope = pegPos.sub(pos);
			rope.rotate90(-1);
			float magnitude = rope.len() * rope.len();
			velocity = rope.scl((rope.dot(velocity) / magnitude));
			setTailLong();
		}else{
			started = true;
			angVel = -0.5f;
			velocity.set(0.1f, 0.4f);
			sprite.bounds.y = baseHeight;
			face.bounds.y = sprite.bounds.y;
		}
	}
	
	public void clearPeg() {
		face.playAnimation("normal");
		peg = null;
		targetAngle = 0;
		setTailNormal();
	}
	
	public void setTailNormal() {
		tail.setVisible(true);
		tailLong.setVisible(false);
		tailPeg.setVisible(false);
	}
	
	public void setTailLong() {
		tail.setVisible(false);
		tailLong.setVisible(true);
		tailPeg.setVisible(true);
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
		this.started = false;
		setTailNormal();
	}

}
