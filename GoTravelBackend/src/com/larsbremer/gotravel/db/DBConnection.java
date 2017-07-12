package com.larsbremer.gotravel.db;

public class DBConnection {

	public static DBController getDatabaseController() {
		return new MongoController();
	}
}
