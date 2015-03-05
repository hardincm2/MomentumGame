package com.brassbeluga.momentum.stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Singleton for use managing the current player's stats and
 * communicating stats to the game.
 * 
 * @author spencer
 * 
 */
public class StatManager {
	
	private Gson gson;

	// The list of player stat objects
	public PlayerStats stats;

	public StatManager() {
		this.gson = new Gson();
		File statFile = Gdx.files.internal("stats.json").file();
		if (!statFile.exists()) {
			try {
				statFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			stats = gson.fromJson(new FileReader(statFile), PlayerStats.class);
		} catch (Exception e) {
			
		}
		if (stats == null) {
			stats = new PlayerStats(0, "default", 0);
		}
	}
	
	public void updateStats(int level) {
		if (level > stats.highScore)
			stats.highScore = level;
		stats.score += level;
		try {
			writeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile() throws IOException {
		if (stats != null) {
			File statFile = Gdx.files.internal("stats.json").file();
			FileWriter writer = new FileWriter(statFile);
			writer.write(gson.toJson(stats));
			writer.close();
		}
	}

}
