package com.larsbremer.gotravel.db.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Location;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoFlight {

	private Flight parseFlightFields(Document doc) {

		Flight flight = new Flight();

		flight.setTripId(doc.getString("tripId"));

		Document departureLocationDoc = (Document) doc.get("departureLocation");
		Location departureLocation = MongoLocation.parseLocationFields(departureLocationDoc);
		flight.setDepartureLocation(departureLocation);

		Document arrivalLocationDoc = (Document) doc.get("arrivalLocation");
		Location arrivalLocation = MongoLocation.parseLocationFields(arrivalLocationDoc);
		flight.setArrivalLocation(arrivalLocation);

		MongoSegment.addSegmentFields(flight, doc);

		return flight;
	}

	public List<Flight> searchFlights(Flight filter, Integer offset, Integer size) throws JsonProcessingException {

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
			flights.add(parseFlightFields(doc));
		}

		return flights;
	}

}
