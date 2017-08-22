package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Location;
import com.larsbremer.gotravel.model.TrainRide;
import com.larsbremer.gotravel.model.Trip;

public class DemoData {

	private static TripController tripController = new TripController();

	public static void main(String[] args) {
		createDemoData();
	}

	private static void createDemoData() {

		// Trip
		Trip createdTrip = createTrip(createDate(2017, 10, 20, 10, 0), createDate(2017, 11, 3, 21, 0));
		System.out.println(createdTrip);

		// Acccomodation
		Accomodation createdAccomodation = createAccomodation(createdTrip.getId(), "Pucllana Lodge",
				createLocation("Lima", "Peru"), createDate(2017, 10, 18, 13, 0), createDate(2017, 10, 19, 12, 0));
		System.out.println(createdAccomodation);

		// Location
		Location london = createLocation("London", "United Kingdom");
		Location luxembourg = createLocation("Luxembourg", "Luxembourg");
		Location newyork = createLocation("New York", "United States of America");
		Location lima = createLocation("Lima", "Peru");
		Location cuzco = createLocation("Cuzco", "Peru");
		Location miami = createLocation("Miami", "United States of America");
		Location puno = createLocation("Puno", "Peru");

		// Flights
		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-417", luxembourg,
				createDate(2017, 10, 17, 11, 30), london, createDate(2017, 10, 17, 12, 0)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-113", london,
				createDate(2017, 10, 17, 16, 5), newyork, createDate(2017, 10, 17, 19, 5)));

		System.out.println(createFlight(createdTrip.getId(), "American Airlines", "AA-7739", newyork,
				createDate(2017, 10, 17, 23, 25), lima, createDate(2017, 10, 18, 5, 55)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-5050", lima,
				createDate(2017, 11, 2, 6, 45), miami, createDate(2017, 11, 2, 13, 42)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-206", miami,
				createDate(2017, 11, 2, 18, 25), london, createDate(2017, 11, 3, 6, 45)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-416", london,
				createDate(2017, 11, 3, 8, 5), luxembourg, createDate(2017, 11, 3, 10, 25)));

		System.out.println(createFlight(createdTrip.getId(), "Avianca", "V54UL8", lima,
				createDate(2017, 10, 19, 14, 42), cuzco, createDate(2017, 10, 19, 16, 2)));

		System.out.println(createBusRide(createdTrip.getId(), "Turismomer", "Sun's Route", cuzco,
				createDate(2017, 10, 26, 7, 0), puno, createDate(2017, 10, 26, 17, 0)));

		// Activity
		String note = "Inka Trail: Day 1";
		Activity createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 22, 12, 00), note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 2";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 23, 12, 00), note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 3";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 24, 12, 00), note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 4";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 25, 12, 00), note);
		System.out.println(createdActivity);

		note = "Train back to Cuzco";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 25, 16, 00), note);
		System.out.println(createdActivity);
	}

	private static Activity createActivity(String tripId, Calendar date, String note) {

		Activity activity = new Activity();
		activity.setDate(date);
		activity.setTripId(tripId);
		activity.setNote(note);

		Activity createdActivity = tripController.createActivity(activity);
		return createdActivity;

	}

	private static Location createLocation(String city, String country) {

		Location location = new Location();
		location.setCity(city);
		location.setCountry(country);
		return location;
	}

	private static Calendar createDate(int year, int month, int day, int hour, int minute) {
		Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		endDate.set(year, month - 1, day, hour, minute);
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

	private static Accomodation createAccomodation(String tripId, String name, Location location, Calendar startDate,
			Calendar endDate) {

		Accomodation accomodation = new Accomodation();
		accomodation.setName(name);
		accomodation.setTripId(tripId);
		accomodation.setStartDate(startDate);
		accomodation.setEndDate(endDate);
		accomodation.setLocation(location);

		Accomodation createdAccomodation = tripController.createAccomodation(accomodation);
		return createdAccomodation;
	}

	private static TrainRide createTrainRide(String tripId, String name, String number, Location startLocation,
			Calendar startDate, Location endLocation, Calendar endDate) {

		TrainRide trainRide = new TrainRide();
		trainRide.setNumber(number);
		trainRide.setName(name);
		trainRide.setTripId(tripId);
		trainRide.setStartDate(startDate);
		trainRide.setDepartureLocation(startLocation);
		trainRide.setEndDate(endDate);
		trainRide.setArrivalLocation(endLocation);

		TrainRide createdTrainRide = tripController.createTrainRide(trainRide);
		return createdTrainRide;
	}

	private static BusRide createBusRide(String tripId, String name, String service, Location startLocation,
			Calendar startDate, Location endLocation, Calendar endDate) {

		BusRide busRide = new BusRide();
		busRide.setService(service);
		busRide.setName(name);
		busRide.setTripId(tripId);
		busRide.setStartDate(startDate);
		busRide.setDepartureLocation(startLocation);
		busRide.setEndDate(endDate);
		busRide.setArrivalLocation(endLocation);

		BusRide createdBusRide = tripController.createBus(busRide);
		return createdBusRide;
	}

	private static Flight createFlight(String tripId, String airline, String number, Location startLocation,
			Calendar startDate, Location endLocation, Calendar endDate) {

		Flight flight = new Flight();
		flight.setNumber(number);
		flight.setAirline(airline);
		flight.setTripId(tripId);
		flight.setStartDate(startDate);
		flight.setDepartureLocation(startLocation);
		flight.setEndDate(endDate);
		flight.setArrivalLocation(endLocation);

		Flight createdFlight = tripController.createFlight(flight);
		return createdFlight;
	}
}
