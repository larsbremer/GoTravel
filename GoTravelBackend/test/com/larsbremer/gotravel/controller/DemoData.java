package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Location;
import com.larsbremer.gotravel.model.Trip;

public class DemoData {

	private static TripController tripController = new TripController();

	public static void main(String[] args) {
		createDemoData();
	}

	private static void createDemoData() {

		// Trip
		Trip createdTrip = createTrip(createDate(2017, 9, 20, 10, 0), createDate(2017, 10, 3, 21, 0));
		System.out.println(createdTrip);

		// Acccomodation
		Accomodation createdAccomodation = createAccomodation(createdTrip.getId(), createLocation("Lima", "Peru"),
				createDate(2017, 9, 28, 12, 0), createDate(2017, 10, 1, 10, 0));
		System.out.println(createdAccomodation);

		// Flight
		Flight createdArrivalFlight = createFlight(createdTrip.getId(), createLocation("Luxembourg", "Luxembourg"),
				createDate(2017, 9, 20, 13, 0), createLocation("Lima", "Peru"), createDate(2017, 9, 20, 20, 0));
		System.out.println(createdArrivalFlight);

		// Flight
		Flight createdDepartureFlight = createFlight(createdTrip.getId(), createLocation("Luxembourg", "Luxembourg"),
				createDate(2017, 10, 3, 2, 0), createLocation("Lima", "Peru"), createDate(2017, 10, 3, 15, 0));
		System.out.println(createdDepartureFlight);

	}

	private static Location createLocation(String city, String country) {

		Location location = new Location();
		location.setCity(city);
		location.setCountry(country);
		return location;
	}

	private static Calendar createDate(int year, int month, int day, int hour, int minute) {
		Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		endDate.set(year, month, day, hour, minute);
		return endDate;
	}

	private static Trip createTrip(Calendar startDate, Calendar endDate) {

		Trip trip = new Trip();
		trip.setName("Peru Trip");
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);

		Trip createdTrip = tripController.createTrip(trip);
		return createdTrip;
	}

	private static Accomodation createAccomodation(String tripId, Location location, Calendar startDate,
			Calendar endDate) {

		Accomodation accomodation = new Accomodation();
		accomodation.setName("Hotel Imagination");
		accomodation.setTripId(tripId);
		accomodation.setStartDate(startDate);
		accomodation.setEndDate(endDate);
		accomodation.setLocation(location);

		Accomodation createdAccomodation = tripController.createAccomodation(accomodation);
		return createdAccomodation;
	}

	private static Flight createFlight(String tripId, Location startLocation, Calendar startDate, Location endLocation,
			Calendar endDate) {

		Flight flight = new Flight();
		flight.setNumber("LHR2392");
		flight.setTripId(tripId);
		flight.setStartDate(startDate);
		flight.setDepartureLocation(startLocation);
		flight.setEndDate(endDate);
		flight.setArrivalLocation(endLocation);

		Flight createdFlight = tripController.createFlight(flight);
		return createdFlight;
	}
}
