package com.brassbeluga.momentum;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brassbeluga.momentum.biomes.Biome;
import com.brassbeluga.momentum.biomes.ForestBiome;
import com.brassbeluga.momentum.biomes.HillsBiome;

public class LevelManager {
	private Map<BiomeType, Biome> biomes;
	private Biome currentBiome;
	
	//private BiomeType transitionToType;
	
	/** Different level biomes for the game environment */
	public static enum BiomeType {
		HILLS, FOREST
	}
	
	/**
	 * Constructs a new level manager.
	 * 
	 */
	public LevelManager() {
		this.biomes = new HashMap<BiomeType, Biome>();
		
		// Add in the different biome types to the mapping.
		biomes.put(BiomeType.HILLS, new HillsBiome());
		biomes.put(BiomeType.FOREST, new ForestBiome());
		//this.transitionToType = null;
	}
	
	/**
	 * Returns the biome marker texture of the given biome type
	 * for use on the progress bar. Returns null if the biome
	 * type does not exist.
	 * @param type The desired biome marker
	 * @return The texture of the biomes marker
	 */
	public TextureRegion getMarker(BiomeType type) {
		Biome biome = biomes.get(type);
		if (biome != null)
			return biome.marker;
		return null;
	}
	
	/**
	 * To be called when transitions between biomes are done.
	 * 
	 * @param type Type of biome transition to.
	 */
	public void transitionTo(BiomeType type) {
		//transitionToType = type;
	}
	
	/**
	 * Sets a new level type biome.
	 * 
	 * @param type The new biome type.
	 */
	public void setLevelType(BiomeType type) {
		this.currentBiome = biomes.get(type);
	}
	
	/**
	 * Generates a fresh biome (should be called on each newLevel()).
	 */
	public void generateLevel() {
		this.currentBiome.generate();
	}
	
	/**
	 * Renders the current level to the batch.
	 * 
	 * @param batch The batch which this level will render to.
	 */
	public void renderLevel(SpriteBatch batch) {
		currentBiome.draw(batch);
	}

	/**
	 * Updates the current biome.
	 * 
	 * @param delta Time since update was last called.
	 */
	public void update(float delta) {
		currentBiome.update(delta);
	}
}
