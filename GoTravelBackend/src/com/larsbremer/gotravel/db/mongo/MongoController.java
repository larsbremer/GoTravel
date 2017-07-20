package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.util.List;

import com.larsbremer.gotravel.controller.ParsingException;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Trip;

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

		try {
			return new MongoTrip().searchTrips(filter, offset, size);
		} catch (IOException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public List<Accomodation> searchAccomodations(Accomodation filter, Integer offset, Integer size) {
		try {
			return new MongoAccomodation().searchAccomodations(filter, offset, size);
		} catch (IOException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public List<Flight> searchFlights(Flight filter, Integer offset, Integer size) {
		try {
			return new MongoFlight().searchFlights(filter, offset, size);
		} catch (IOException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public void createTrip(Trip trip) {

		try {
			new MongoTrip().createTrip(trip);
		} catch (IOException e) {
			throw new ParsingException(e);
		}
	}

}
