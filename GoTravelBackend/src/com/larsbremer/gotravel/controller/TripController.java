package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.List;

import com.larsbremer.gotravel.db.DBConnection;
import com.larsbremer.gotravel.db.DBController;
import com.larsbremer.gotravel.model.Accommodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.DateSegment;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Segment;
import com.larsbremer.gotravel.model.TrainRide;
import com.larsbremer.gotravel.model.Trip;

public class TripController {

	DBController dbController = null;

	public TripController() {
		dbController = DBConnection.getDatabaseController();
	}

	public List<Trip> getTrips() throws Exception {

		return dbController.searchTrips(new Trip(), null, null);
	}

	public Trip getTrip(String id) throws Exception {

		return getTrip(id, false);
	}

	public Trip getTrip(String id, boolean expand) throws Exception {

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

	public Flight getFlight(String id) throws Exception {

		Flight flightFilter = new Flight();
		flightFilter.setId(id);

		List<Flight> flights = dbController.searchFlights(flightFilter, 0, 1);

		if (flights == null || flights.isEmpty()) {
			return null;
		}

		return flights.get(0);
	}

	public Flight updateFlight(String flightId, Flight newFlight) throws Exception {

		return dbController.updateFlight(flightId, newFlight);
	}

	private void expand(Trip trip) throws Exception {

		expandTransportations(trip);

		expandDateSegments(trip);

		expandAccommodations(trip);

		expandActivities(trip);

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

	private void expandAccommodations(Trip trip) throws Exception {

		if (trip == null) {
			return;
		}

		List<Accommodation> accommodations = getAccommodationsForTrip(trip.getId());

		addAccommodationsToTrip(trip, accommodations);
	}

	private void expandActivities(Trip trip) throws Exception {

		if (trip == null) {
			return;
		}

		List<Activity> activities = getActivitiesForTrip(trip.getId());

		addActivitiesToTrip(trip, activities);
	}

	private void addActivitiesToTrip(Trip trip, List<Activity> activities) {

		for (Activity activity : activities) {
			addActivityToTrip(trip, activity);
		}
	}

	private void addActivityToTrip(Trip trip, Activity activity) {

		List<Segment> segments = trip.getSegments();
		for (Segment segment : segments) {

			Calendar startDate = activity.getStartDate();
			boolean startDateInSegment = startDate == null
					|| SegmentController.doesSegmentContainDate(segment, startDate);

			Calendar endDate = activity.getEndDate();
			boolean endDateInSegment = endDate == null || SegmentController.doesSegmentContainDate(segment, endDate);

			if (segment instanceof DateSegment && startDateInSegment && endDateInSegment) {
				((DateSegment) segment).addActivity(activity);
			}
		}
	}

	private void addAccommodationsToTrip(Trip trip, List<Accommodation> accommodations) {

		for (Accommodation accommodation : accommodations) {
			addAccommodationToTrip(trip, accommodation);
		}
	}

	private void addAccommodationToTrip(Trip trip, Accommodation accommodation) {

		List<Segment> segments = trip.getSegments();
		for (Segment segment : segments) {
			if (segment instanceof DateSegment && SegmentController.isEndOfDay(segment.getEndDate())
					&& SegmentController.doesSegmentContainDate(accommodation, segment.getEndDate())) {
				((DateSegment) segment).setEveningAccommodation(accommodation);
			}
		}
	}

	private void expandTransportations(Trip trip) throws Exception {

		if (trip == null) {
			return;
		}

		List<Flight> flights = getFlightsForTrip(trip.getId());
		addSegmentsToTrip(trip, flights);

		List<TrainRide> trainRides = getTrainRidesForTrip(trip.getId());
		addSegmentsToTrip(trip, trainRides);

		List<BusRide> busRides = getBusRidesForTrip(trip.getId());
		addSegmentsToTrip(trip, busRides);
	}

	private void addSegmentsToTrip(Trip trip, List<? extends Segment> segments) {
		for (Segment segment : segments) {
			trip.addSegment(segment);
		}
	}

	private List<Flight> getFlightsForTrip(String tripId) throws Exception {

		Flight flightFilter = new Flight();
		flightFilter.setTripId(tripId);

		return dbController.searchFlights(flightFilter, -1, -1);
	}

	private List<TrainRide> getTrainRidesForTrip(String tripId) throws Exception {

		TrainRide trainRideFilter = new TrainRide();
		trainRideFilter.setTripId(tripId);

		return dbController.searchTrainRides(trainRideFilter, -1, -1);
	}

	private List<BusRide> getBusRidesForTrip(String tripId) {

		BusRide busRideFilter = new BusRide();
		busRideFilter.setTripId(tripId);

		return dbController.searchBusRides(busRideFilter, -1, -1);
	}

	private List<Accommodation> getAccommodationsForTrip(String tripId) throws Exception {

		Accommodation accommodationFilter = new Accommodation();
		accommodationFilter.setTripId(tripId);

		return dbController.searchAccommodations(accommodationFilter, -1, -1);
	}

	private List<Activity> getActivitiesForTrip(String tripId) throws Exception {

		Activity activityFilter = new Activity();
		activityFilter.setTripId(tripId);

		return dbController.searchActivities(activityFilter, -1, -1);
	}

	public Trip createTrip(Trip trip) {
		return dbController.createTrip(trip);
	}

	public Accommodation createAccommodation(Accommodation accommodation) {
		return dbController.createAccommodation(accommodation);
	}

	public Flight createFlight(Flight flight) {
		return dbController.createFlight(flight);
	}

	public TrainRide createTrainRide(TrainRide trainRide) {
		return dbController.createTrainRide(trainRide);
	}

	public BusRide createBus(BusRide busRide) {
		return dbController.createBusRide(busRide);
	}

	public Activity createActivity(Activity activity) {
		return dbController.createActivity(activity);
	}

}
