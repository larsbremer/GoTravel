package com.larsbremer.gotravel.db;

import com.larsbremer.gotravel.db.mongo.MongoController;

public class DBConnection {

	public static DBController getDatabaseController() {
		return new MongoController();
	}
}
