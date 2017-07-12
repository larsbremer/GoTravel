package com.larsbremer.gotravel.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.larsbremer.gotravel.model.Trip;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

class MongoController implements DBController {

	private static final String MONGO_ID = "_id";

	private enum Collection {

		ACCOMODATION, ACTIVITY, FLIGHT, TRIP;

		public String getCollectionName() {
			return this.name().toLowerCase();
		}
	}

	private static final String DBName = "gotravel";

	private MongoClient mongoClient;
	private MongoDatabase database;

	public MongoController() {

		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase(DBName);

	}

	public MongoCollection<Document> getCollection(Collection col) {
		return database.getCollection(col.getCollectionName());
	}

	@Override
	public List<Trip> searchTrip(Trip filter, Integer offset, Integer size) {

		MongoCollection<Document> collection = getCollection(Collection.TRIP);

		FindIterable<Document> search = collection.find();

		Bson bsonFilter = getBsonFilter(filter);
		if (filter != null) {
			search.filter(bsonFilter);
		}

		if (offset != null) {
			search.skip(offset);
		}

		if (size != null) {
			search.limit(size);
		}

		List<Trip> trips = new ArrayList<>();
		for (Document doc : search) {
			trips.add(getTrip(doc));
		}

		return trips;
	}

	private Trip getTrip(Document doc) {

		Trip trip = new Trip();

		trip.setId(doc.getObjectId(MONGO_ID).toHexString());
		trip.setName(doc.getString("name"));

		if (doc.containsKey("startDate")) {
			Calendar c1 = Calendar.getInstance();
			c1.setTimeInMillis(doc.getLong("startDate"));
			trip.setStartDate(c1);
		}

		if (doc.containsKey("endDate")) {
			Calendar c2 = Calendar.getInstance();
			c2.setTimeInMillis(doc.getLong("endDate"));
			trip.setEndDate(c2);
		}

		return trip;
	}

	private Bson getBsonFilter(Trip filter) {

		BasicDBObject bsonFilter = new BasicDBObject();

		if (filter == null) {
			return bsonFilter;
		}

		if (filter.getId() != null) {
			bsonFilter.put(MONGO_ID, new ObjectId(filter.getId()));
		}

		bsonFilter.put("name", filter.getName());

		return bsonFilter;
	}

	@Override
	public void close() {
		mongoClient.close();
	}
}
