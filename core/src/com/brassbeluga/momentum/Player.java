package com.brassbeluga.momentum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
	public static float HEIGHT = 10;
	public static float WIDTH = 10;
	public static float TARGET_ANGLE_VEL = 10;
	public static float MAX_ANG_VEL = 5;
	public static float ANG_DECAY = 0.995f;
	public static float SPEED_THRESHOLD = 2.0f; 
	public Vector2 tailOff = new Vector2(-43, -43);
	
	public boolean dead;

	public Bird bird; // null if currently not attached
	public float pegAngle;
	public float angVel = 0;
	public float swingRadius;
	public float lastAngle;
	public float targetAngle;
	
	public static float BOOST = 1.008f;
	public static float BOOST_TOLERANCE = 10f;
	public boolean isBoosting = false;
	
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
	
	public ParticleEffectPool partPoolBird;
	public ParticleEffectPool partPoolDirt;
	public ParticleEffect partBird;
	public ParticleEffect partDirt;
	
	public Player(float x, float y, TextureRegion texture, World world) {
		super(x, y,texture);
		dead = false;
		angle = 0.0f;
		targetAngle = angle;
		lastAngle = angle;
		pegAngle = angle;
		started = false;
		
		
		partPoolBird = new ParticleEffectPool(Assets.partFeathers, 50, 100);
		partPoolDirt = new ParticleEffectPool(Assets.partDirt, 100, 200);
		partDirt = partPoolDirt.obtain();
		partDirt.allowCompletion();
		
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
		if (started && !dead) {
			velocity.add(gravity);
			x += velocity.x;
			y += velocity.y;
			
			if (bird != null) {
				Vector2 pegPos = new Vector2(bird.x, bird.y);
				Vector2 newPos = new Vector2(x, y);
				pegPos.set(bird.x, bird.y);
				Vector2 rope = newPos.sub(pegPos).setLength(swingRadius);
				pegPos.add(rope);
				x = pegPos.x;
				y = pegPos.y;
				
				pos.set(x, y);
				pegPos.set(bird.x, bird.y);
				rope = pegPos.sub(pos);
				
				rope.setLength(velocity.len());
				pegAngle = rope.angle();
				rope.rotate90(-1);
				targetAngle = rope.angle() - 90;
				float magnitude = rope.len() * rope.len();
				if (isBoosting)
					velocity.scl(BOOST);
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
				if (velocity.x < SPEED_THRESHOLD) {
					angle += angVel;
					angVel *= ANG_DECAY;
				}else{
					angle = (float) (MathUtils.radiansToDegrees * Math.atan2(pos.y - lastPos.y, pos.x - lastPos.x)) - 90;
					lastPos.set(x, y);
				}
			}
			tail.angle = MathUtils.clamp(tail.angle - angVel, -TAIL_ANGLE_MAX / 6f, 60);
			
			float diff = angleDiff(0, tail.angle);
			float dir = Math.signum(diff);
			if (Math.abs(diff) <= TAIL_SPEED)
				tail.angle = tailTarget;
			else
				tail.angle += dir * TAIL_SPEED;
		}else if (!dead){
			waveDelta += Math.PI / 100;
			tail.angle = (float) (Math.sin(waveDelta) * 20f);
			sprite.bounds.y = (float) (baseHeight + baseHeight / BREATH_FACTOR
					+ (baseHeight / BREATH_FACTOR) * Math.sin(waveDelta));
			face.bounds.y = sprite.bounds.y;
		}else{ // if dead
			velocity.y = 0;
			velocity.x *= 0.96;
			x += velocity.x;
			if (velocity.x <= 0.05f)
				reset(World.PLAYER_START_X, World.PLAYER_START_Y);
		}
		if (Math.random() < 0.01) {
			face.playAnimation("blink");
		}
	}
	
	private float angleDiff(float a1, float a2) {
		return ((((a1 - a2) % 360f) + 540f) % 360f) - 180f;
	}
	
	public void setDead() {
		clearPeg();
		velocity.y = 0;
		dead = true;
		partDirt.start();
		partBird = null;
	}
	
	// For effect use
	public void postRender(SpriteBatch batch) {
		if (partBird != null) {
			partBird.update(Gdx.graphics.getDeltaTime());
			partBird.draw(batch);
		}
		if (partDirt != null) {
			partDirt.update(Gdx.graphics.getDeltaTime());
			partDirt.setPosition(x, y - sprite.bounds.y / 4.0f);
			if (dead)
				partDirt.draw(batch);
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// Angling, scaling, and positioning the tail to the peg
		if (bird != null) {
			Vector2 tailPos = new Vector2(tailOff.x, tailOff.y).rotate(angle);
			tailPos.set(tailPos.x + x, tailPos.y + y);
			Vector2 pegTail = new Vector2(bird.x - tailPos.x, bird.y - tailPos.y);
			tailLong.hasAbsoluteAngle = true;
			tailLong.angle = pegTail.angle();
			tailLong.bounds.x = pegTail.len();
			tailPeg.position.set(bird.x, bird.y);
			tailPeg.angle = pegTail.angle() - 140;
		}
		sprite.position.set(x, y);
		sprite.angle = angle;
		sprite.draw(batch);
		tailPeg.draw(batch);
	}
	
	public void setPeg(Bird bird) {
		face.playAnimation("eyeclose");
		if (started && !dead) {
			this.bird = bird;
			bird.held = true;
			if (partBird == null)
				partBird = partPoolBird.obtain();
			partBird.setPosition(bird.x, bird.y);
			partBird.start();
			Vector2 pos = new Vector2(x, y);
			Vector2 pegPos = new Vector2(bird.x, bird.y);
			swingRadius = pos.dst(pegPos);
			Vector2 rope = pegPos.sub(pos);
			rope.rotate90(-1);
			float magnitude = rope.len() * rope.len();
			if (Math.abs(rope.angle(velocity)) < BOOST_TOLERANCE)
				isBoosting = true;
			velocity = rope.scl((rope.dot(velocity) / magnitude));
			setTailLong();
		}else if (!dead) {
			started = true;
			angVel = -0.5f;
			velocity.set(0.1f, 0.4f);
			sprite.bounds.y = baseHeight;
			face.bounds.y = sprite.bounds.y;
		}
	}
	
	public void clearPeg() {
		face.playAnimation("normal");
		if (bird != null)
			bird.held = false;
		bird = null;
		targetAngle = 0;
		isBoosting = false;
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
		this.bird = null;
		this.angVel = 0;
		this.angle = 0;
		this.lastAngle = 0;
		this.started = false;
		setTailNormal();
		nextScreen();
		partDirt.allowCompletion();
	}
	
	public void nextScreen() {
		if (partBird != null) {
			partPoolBird.free((PooledEffect) partBird);
			partBird = null;
		}
		if (partDirt != null) {
			//partPoolDirt.free((PooledEffect) partDirt);
			//partDirt = null;
		}
	}

}
