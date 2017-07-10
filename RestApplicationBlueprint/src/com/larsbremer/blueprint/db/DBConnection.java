package com.larsbremer.blueprint.db;

public class DBConnection {

	public static DBController getDatabaseController() {
		return new MongoController();
	}
}
