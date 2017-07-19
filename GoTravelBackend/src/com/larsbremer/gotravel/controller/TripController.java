package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.List;

import com.larsbremer.gotravel.db.DBConnection;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.DateSegment;
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

		expandDateSegments(trip);

		expandAccomodations(trip);

	}

	private void expandDateSegments(Trip trip) {
		List<Segment> segments = trip.getSegments();

		for (int i = 0; i < segments.size() - 1; i++) {

			Segment s1 = segments.get(i);
			Segment s2 = segments.get(i + 1);

			if (SegmentController.segmentsFollowEachOther(s1, s2)) {
				continue;
			}

			DateSegment ds = new DateSegment();

			Calendar startDate = (Calendar) s1.getEndDate().clone();

			startDate.add(Calendar.SECOND, 1);
			ds.setStartDate(startDate);

			Calendar currentEndDay = SegmentController.getEndOfDay(s1.getEndDate());
			ds.setEndDate(currentEndDay);

			while (currentEndDay.before(s2.getStartDate())) {

				trip.addSegment(ds);

				currentEndDay = (Calendar) currentEndDay.clone();
				currentEndDay.add(Calendar.DATE, 1);

				ds = new DateSegment();

				startDate = SegmentController.getBeginningOfDay(currentEndDay);
				ds.setStartDate(startDate);

				ds.setEndDate((Calendar) currentEndDay.clone());
			}

			Calendar endDate = (Calendar) s2.getStartDate().clone();
			endDate.add(Calendar.SECOND, -1);
			ds.setEndDate(endDate);
			trip.addSegment(ds);

		}

	}

	private void expandAccomodations(Trip trip) throws Exception {

		if (trip == null) {
			return;
		}

		List<Accomodation> accomodations = getAccomodationsForTrip(trip.getId());

		addAccomodationsToTrip(trip, accomodations);
	}

	private void addAccomodationsToTrip(Trip trip, List<Accomodation> accomodations) {

		for (Accomodation accomodation : accomodations) {
			addAccomodationToTrip(trip, accomodation);
		}
	}

	private void addAccomodationToTrip(Trip trip, Accomodation accomodation) {

		List<Segment> segments = trip.getSegments();
		for (Segment segment : segments) {
			if (segment instanceof DateSegment && SegmentController.doSegmentsOverlap(segment, accomodation)) {
				((DateSegment) segment).setEveningAccomodation(accomodation);
			}
		}
	}

	private void expandFlights(Trip trip) throws Exception {

		if (trip == null) {
			return;
		}

		List<Flight> flights = getFlightsForTrip(trip.getId());
		addSegmentsToTrip(trip, flights);
	}

	private void addSegmentsToTrip(Trip trip, List<? extends Segment> segments) {
		for (Segment segment : segments) {
			trip.addSegment(segment);
		}
	}

	private List<Flight> getFlightsForTrip(String tripId) throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			Flight flightFilter = new Flight();
			flightFilter.setTripId(tripId);

			return dbController.searchFlights(flightFilter, -1, -1);
		}
	}

	private List<Accomodation> getAccomodationsForTrip(String tripId) throws Exception {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			Accomodation accomodationFilter = new Accomodation();
			accomodationFilter.setTripId(tripId);

			return dbController.searchAccomodations(accomodationFilter, -1, -1);
		}
	}
}