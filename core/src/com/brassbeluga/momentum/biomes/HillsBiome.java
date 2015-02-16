package com.brassbeluga.momentum.biomes;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.brassbeluga.momentum.Assets;
import com.brassbeluga.momentum.GameSprite;
import com.brassbeluga.momentum.World;
import com.brassbeluga.momentum.gameobjects.Cloud;
import com.brassbeluga.momentum.gameobjects.GameObject;

public class HillsBiome extends Biome{
	protected Array<Cloud> clouds;
	protected Array<GameObject> bushes;

	public HillsBiome() {
		this.ground = new GameSprite(Assets.ground_hills, 0, 0);
		this.backTile =  new GameSprite(Assets.back_tile_hills, 0, 0);
		this.backGround = new GameSprite(Assets.back_dist_hills, 0, 0);
		this.marker = Assets.markerHills;
		
		clouds = new Array<Cloud>();
		bushes = new Array<GameObject>();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		// Draw all clouds
		for (Cloud cloud : clouds) {
			cloud.render(batch);
		}
		
		// Draw all bushes.
		for (GameObject bush : bushes) {
			bush.render(batch);
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		for (Cloud cloud : clouds) {
			cloud.update(World.gravity);
		}
	}
	
	@Override
	public void generate() {
		
		// Create new clouds
		clouds.clear();
		for (int i = 0; i < Math.round(Math.random() * 25.0); i++) {
			Cloud cloud = new Cloud((float) (Math.random() * World.WORLD_WIDTH), 
					(float) (World.WORLD_HEIGHT - Math.random() * 4.0f), Assets.clouds[MathUtils.random(3)]);
			cloud.sprite.scale.x = cloud.sprite.scale.y = MathUtils.random(0.2f) - 0.1f + 1.0f;
			clouds.add(cloud);
		}
		
		// Create new bushes
		bushes.clear();
		for (int i = 0; i < Math.round(Math.random() * 25.0); i++) {
			GameObject bush = new GameObject((float) (Math.random() * World.WORLD_WIDTH), 
					(float) (8 + Math.random() * 4.0f), Assets.bushes[MathUtils.random(2)]);
			bush.sprite.angle = MathUtils.random(20) - 10f;
			bush.sprite.scale.x = bush.sprite.scale.y = MathUtils.random(0.2f) - 0.1f + 1.0f;
			boolean added = false;
			for (int p = 0; p < bushes.size; p++) {
				if (bushes.get(p).y < bush.y) {
					bushes.insert(p, bush);
					added = true;
					break;
				}
			}
			if (!added)
				bushes.add(bush);
		}
	}
}
