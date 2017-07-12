package com.larsbremer.gotravel.controller;

import java.util.List;

import com.larsbremer.gotravel.db.DBConnection;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Trip;

public class TripController {

	public TripController() {

	}

	public List<Trip> getTrips(Object object) throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			return dbController.searchTrip(null, null, null);
		}
	}

	public Trip getTrip(String id) throws Exception {

		return getTrip(id, false);
	}

	public Trip getTrip(String id, boolean expand) throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			Trip tripFilter = new Trip();
			tripFilter.setId(id);

			List<Trip> result = dbController.searchTrip(tripFilter, 0, 1);

			if (result.isEmpty()) {
				return null;
			}

			Trip trip = result.get(0);

			if (expand) {
				return expand(trip);
			}

			return trip;
		}
	}

	private Trip expand(Trip trip) {
		// TODO implement
		return trip;
	}
}
