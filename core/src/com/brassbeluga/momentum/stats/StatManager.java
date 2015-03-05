package com.brassbeluga.momentum.stats;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;

/**
 * Singleton for use managing the current player's stats and communicating stats
 * to the game.
 * 
 * @author spencer
 * 
 */
public class StatManager {

	private Gson gson;

	// The list of player stat objects
	public PlayerStats stats;
	public Array<PlayerUpgrade> upgrades;

	public StatManager() {
		this.gson = new Gson();
		File statFile = Gdx.files.internal("stats.json").file();
		try {
			stats = gson.fromJson(new FileReader(statFile), PlayerStats.class);
		} catch (Exception e) {
		}
		if (stats == null)
			stats = new PlayerStats(0, "default", 0);
		File upgradeFile = Gdx.files.internal("upgrades.json").file();
		upgrades = new Array<PlayerUpgrade>();
		try {
			Scanner input = new Scanner(upgradeFile);
			while (input.hasNext())
				upgrades.add(gson.fromJson(input.nextLine(),
						PlayerUpgrade.class));
			input.close();
		} catch (Exception e) {
		}
	}

	public void updateStats(int level) {
		System.out.println(upgrades.size);
		if (level > stats.highScore)
			stats.highScore = level;
		stats.score += level;
		for (PlayerUpgrade upgrade : upgrades) {
			if (!upgrade.unlocked) {
				if (stats.score >= upgrade.cost) {
					upgrade.unlocked = true;
					System.out.println("Unlocked " + upgrade.name);
				}
			}
		}
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
		if (upgrades != null) {
			File upFile = Gdx.files.internal("upgrades.json").file();
			FileWriter writer = new FileWriter(upFile);
			for (PlayerUpgrade upgrade : upgrades) {
				writer.write(gson.toJson(upgrade) + "\n");
			}
			writer.close();
		}
	}

}
