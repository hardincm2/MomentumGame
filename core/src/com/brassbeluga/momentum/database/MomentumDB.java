package com.brassbeluga.momentum.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MomentumDB {
	private DB db;

	/**
	 * Default no-arg constructor.
	 */
	public MomentumDB() {
		MongoClient mongoClient = null;

		try {
			MongoCredential credential = MongoCredential
					.createMongoCRCredential("tempuser", "momentum_db",
							"password".toCharArray());

			mongoClient = new MongoClient(new ServerAddress(
					InetAddress.getByName("ds043991.mongolab.com"), 43991),
					Arrays.asList(credential));
		} catch (UnknownHostException e) {
			System.err.println("Error contacting host: " + e.getMessage());
			e.printStackTrace();
		}

		db = mongoClient.getDB("momentum_db");
	}

	/**
	 * Get's all the highscores currently in the highscore list
	 * 
	 * @return A List of Highscore objects.
	 */
	public List<Highscore> getHighscores() {
		DBCollection collection = db.getCollection("highscores");
		DBCursor cursor = collection.find().sort(new BasicDBObject("score",-1));

		StringBuilder response = new StringBuilder();

		// Build up a well-formed JSON string from the high scores in
		// MongoDB.
		response.append("[");
		while (cursor.hasNext()) {
			response.append(cursor.next().toString());

			if (cursor.hasNext()) {
				response.append(",");
			}
		}
		response.append("]");

		// Parse the JSON into a list of Highscore objects to return.
		List<Highscore> highscores = new Gson().fromJson(response.toString(),
				new TypeToken<List<Highscore>>() {}.getType());
		
		return highscores;
	}

	/**
	 * Submits a highscore to the database.
	 * 
	 * @param username Username of player.
	 * @param score Score achieved.
	 */
	public void submitHighscore(String username, int score) {
		BasicDBObject document = new BasicDBObject();
		document.put("name", username);
		document.put("score", score);

		DBCollection collection = db.getCollection("highscores");
		collection.insert(document);
	}

}
