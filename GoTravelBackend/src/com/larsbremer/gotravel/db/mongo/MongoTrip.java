package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Trip;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoTrip {

	private Trip parseTrip(Document doc) {

		Trip trip = new Trip();

		trip.setName(doc.getString("name"));

		MongoSegment.addSegmentFields(trip, doc);

		return trip;
	}

	public List<Trip> searchTrips(Trip filter, Integer offset, Integer size) throws JsonProcessingException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.TRIP);

		FindIterable<Document> search = collection.find();

		Bson bsonFilter = MongoParser.convertPojoToBson(filter);
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
			trips.add(parseTrip(doc));
		}

		return trips;
	}

	public Trip createTrip(Trip trip) throws IOException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.TRIP);

		Document doc = MongoParser.convertPojoToDocument(trip);
		collection.insertOne(doc);

		return MongoParser.getObject(doc, Trip.class);

	}

}
