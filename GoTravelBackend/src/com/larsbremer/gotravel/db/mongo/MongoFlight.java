package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Flight;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoFlight {

	public List<Flight> search(Flight filter, Integer offset, Integer size) throws IOException, ParseException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.FLIGHT);

		FindIterable<Document> search = collection.find();

		Bson bsonFilter = MongoParser.convertPojoToBson(filter);
		if (filter != null) {
			search.filter(bsonFilter);
		}

		if (offset != null && offset > 0) {
			search.skip(offset);
		}

		if (size != null && size > 0) {
			search.limit(size);
		}

		List<Flight> flights = new ArrayList<>();
		for (Document doc : search) {
			Flight flight = MongoParser.getObject(doc, Flight.class);
			flights.add(flight);
		}

		return flights;
	}

	public Flight create(Flight flight) throws ParseException, IOException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.FLIGHT);

		Document doc = MongoParser.convertPojoToDocument(flight);
		collection.insertOne(doc);

		return MongoParser.getObject(doc, Flight.class);
	}

}
