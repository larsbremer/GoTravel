package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.larsbremer.gotravel.controller.ParsingException;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.TrainRide;
import com.larsbremer.gotravel.model.Trip;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoController implements DBController {

	@Override
	public Trip searchTrip(Trip filter) {

		List<Trip> result = searchTrips(filter, 0, 1);

		if (result.size() == 0) {
			return null;
		}

		return result.get(0);
	}

	@Override
	public List<Trip> searchTrips(Trip filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.TRIP);
	}

	@Override
	public List<Accomodation> searchAccomodations(Accomodation filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.ACCOMODATION);
	}

	@Override
	public List<Flight> searchFlights(Flight filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.FLIGHT);
	}

	@Override
	public List<TrainRide> searchTrainRides(TrainRide filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.TRAIN_RIDE);
	}

	@Override
	public Trip createTrip(Trip trip) {
		return createMongoObject(trip, Collection.TRIP);
	}

	@Override
	public Accomodation createAccomodation(Accomodation accomodation) {
		return createMongoObject(accomodation, Collection.ACCOMODATION);
	}

	@Override
	public List<Activity> searchActivities(Activity filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.ACTIVITY);
	}

	@Override
	public Flight createFlight(Flight flight) {
		return createMongoObject(flight, Collection.FLIGHT);
	}

	@Override
	public Activity createActivity(Activity activity) {
		return createMongoObject(activity, Collection.ACTIVITY);
	}

	@Override
	public TrainRide createTrainRide(TrainRide trainRide) {
		return createMongoObject(trainRide, Collection.TRAIN_RIDE);
	}

	@SuppressWarnings("unchecked")
	public <T> T createMongoObject(T obj, Collection collection) {

		try {

			MongoCollection<Document> mongoCollection = MongoConnection.getDatabaseCollection(collection);

			Document doc = MongoParser.convertPojoToDocument(obj);
			mongoCollection.insertOne(doc);

			return (T) MongoParser.getObject(doc, obj.getClass());

		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	public <T> List<T> searchMongoObject(T filter, Integer offset, Integer size, Collection collection) {

		try {
			MongoCollection<Document> mongoCollection = MongoConnection.getDatabaseCollection(collection);

			FindIterable<Document> search = mongoCollection.find();

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

			List<T> results = new ArrayList<>();
			for (Document doc : search) {

				@SuppressWarnings("unchecked")
				T result = (T) MongoParser.getObject(doc, filter.getClass());
				results.add(result);
			}

			return results;

		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}

	}

	@Override
	public BusRide createBusRide(BusRide busRide) {
		return createMongoObject(busRide, Collection.BUS_RIDE);
	}

	@Override
	public List<BusRide> searchBusRides(BusRide filter, Integer offset, Integer size) {
		return searchMongoObject(filter, offset, size, Collection.BUS_RIDE);
	}
}
