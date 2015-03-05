package com.brassbeluga.momentum.stats;

public class PlayerUpgrade {

	public int cost;
	public String name;
	public String desc;
	public boolean unlocked;
	
	public PlayerUpgrade(int cost, String name, String desc, boolean unlocked) {
		this.cost = cost;
		this.name = name;
		this.desc = desc;
		this.unlocked = unlocked;
	}
	
}
