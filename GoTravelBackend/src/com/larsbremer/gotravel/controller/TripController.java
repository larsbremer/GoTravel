package com.larsbremer.gotravel.controller;

import java.util.ArrayList;
import java.util.List;

import com.larsbremer.gotravel.db.DBConnection;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Segment;
import com.larsbremer.gotravel.model.Trip;

public class TripController {

	public TripController() {

	}

	public List<Trip> getTrips() throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			return dbController.searchTrips(null, null, null);
		}
	}

	public Trip getTrip(String id) throws Exception {

		return getTrip(id, false);
	}

	public Trip getTrip(String id, boolean expand) throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			Trip tripFilter = new Trip();
			tripFilter.setId(id);

			Trip trip = dbController.searchTrip(tripFilter);

			if (trip == null) {
				return trip;
			}

			if (expand) {
				expand(trip);
			}

			return trip;
		}
	}

	private void expand(Trip trip) throws Exception {

		expandFlights(trip);
	}

	private void expandFlights(Trip trip) throws Exception {

		List<Flight> flights = getFlightsForTrip(trip);
		addSegmentsToTrip(trip, flights);
	}

	private void addSegmentsToTrip(Trip trip, List<? extends Segment> segments) {
		for (Segment segment : segments) {
			addSegmentToTrip(trip, segment);
		}
	}

	private void addSegmentToTrip(Trip trip, Segment segment) {

		List<Segment> segments = trip.getSegments();

		if (segments.size() == 0) {
			trip.addSegment(segment);
			return;
		}

	}

	private List<Flight> getFlightsForTrip(Trip trip) throws Exception {

		if (trip == null) {
			return new ArrayList<>();
		}

		try (DBController dbController = DBConnection.getDatabaseController()) {

			Flight flightFilter = new Flight();
			flightFilter.setTripId(trip.getId());

			return dbController.searchFlights(flightFilter, -1, -1);
		}
	}
}
