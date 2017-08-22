package com.larsbremer.gotravel.db.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

	private static final String DBName = "gotravel";

	enum Collection {

		ACCOMODATION, ACTIVITY, FLIGHT, TRIP, DATE_SEGMENT, TRAIN_RIDE, BUS_RIDE;

		public String getCollectionName() {
			return this.name().toLowerCase();
		}
	}

	public static MongoCollection<Document> getDatabaseCollection(Collection col) {
		return getDatabase().getCollection(col.getCollectionName());
	}

	private static class ConnectionHolder {

		static MongoClient mongoClient = new MongoClient("localhost", 27017);
		static final MongoDatabase DATABASE_INSTANCE = mongoClient.getDatabase(DBName);
	}

	private static MongoDatabase getDatabase() {
		return ConnectionHolder.DATABASE_INSTANCE;
	}

}
