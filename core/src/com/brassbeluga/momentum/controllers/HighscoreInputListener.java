package com.brassbeluga.momentum.controllers;

import com.badlogic.gdx.Input.TextInputListener;
import com.brassbeluga.momentum.database.MomentumDB;
import com.brassbeluga.momentum.screens.DeathScreen;

public class HighscoreInputListener implements TextInputListener {
	private MomentumDB db;
	private DeathScreen deathScreen;
	
	public HighscoreInputListener(DeathScreen deathScreen) {
		super();
		this.deathScreen = deathScreen;
		this.db = new MomentumDB();
	}
	
	@Override
	public void input(String text) {
		db.submitHighscore(text, deathScreen.getLevels());
		deathScreen.setHighscores(db.getHighscores());
	}

	
	@Override
	public void canceled() {
	}
}
