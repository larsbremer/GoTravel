package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.larsbremer.gotravel.controller.ParsingException;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Activity;
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
			return new MongoTrip().search(filter, offset, size);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public List<Accomodation> searchAccomodations(Accomodation filter, Integer offset, Integer size) {
		try {
			return new MongoAccomodation().search(filter, offset, size);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public List<Flight> searchFlights(Flight filter, Integer offset, Integer size) {
		try {
			return new MongoFlight().search(filter, offset, size);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public Trip createTrip(Trip trip) {

		try {
			return new MongoTrip().create(trip);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public Accomodation createAccomodation(Accomodation accomodation) {
		try {
			return new MongoAccomodation().create(accomodation);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public List<Activity> searchActivities(Activity filter, Integer offset, Integer size) {
		try {
			return new MongoActivity().search(filter, offset, size);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public Flight createFlight(Flight flight) {
		try {
			return new MongoFlight().create(flight);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public Activity createActivity(Activity activity) {
		try {
			return new MongoActivity().create(activity);
		} catch (IOException | ParseException e) {
			throw new ParsingException(e);
		}
	}
}
