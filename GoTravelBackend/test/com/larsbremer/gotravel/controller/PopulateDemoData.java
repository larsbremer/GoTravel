package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.larsbremer.gotravel.model.Accommodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Location;
import com.larsbremer.gotravel.model.Trip;

public class PopulateDemoData {

	private static TripController tripController = new TripController();

	public static void main(String[] args) {
		createDemoData();
	}

	private static void createDemoData() {

		// Trip
		Trip createdTrip = createTrip(createDate(2017, 10, 20, 10, 0), createDate(2017, 11, 3, 21, 0));
		System.out.println(createdTrip);

		// Location
		Location london = createLocation("London", "United Kingdom");
		Location luxembourg = createLocation("Luxembourg", "Luxembourg");
		Location newyork = createLocation("New York", "United States of America");
		Location lima = createLocation("Lima", "Peru");
		Location cuzco = createLocation("Cuzco", "Peru");
		Location miami = createLocation("Miami", "United States of America");
		Location puno = createLocation("Puno", "Peru");
		Location arequipa = createLocation("Arequipa", "Peru");
		Location paracas = createLocation("Paracas", "Peru");

		// Acccommodation
		String url = "https://secure.booking.com/myreservations.html?aid=304142;label=gen173nr-1DCAEoggJCAlhYSDNiBW5vcmVmaIkBiAEBmAEHuAEHyAEN2AED6AEBkgIBeagCAw;sid=843d82863c6586b1e75a5583e8cb45c2;auth_key=r5UBuUl3VYXp2wDF&";
		Accommodation createdAccommodation = createAccommodation(createdTrip.getId(), "Pucllana Lodge", lima, url,
				createDate(2017, 10, 18, 13, 0), createDate(2017, 10, 19, 12, 0));
		System.out.println(createdAccommodation);

		String url2 = "https://secure.booking.com/myreservations.html?aid=304142;label=gen173nr-1FCAEoggJCAlhYSDNiBW5vcmVmaIkBiAEBmAExuAEHyAEN2AEB6AEB-AECkgIBeagCAw;sid=14ee120a76a3c31b9149dba2bd41e231;auth_key=p2Y1Jy0B72EElnEZ&";
		System.out.println(createAccommodation(createdTrip.getId(), "Hostal Mallqui", cuzco, url2,
				createDate(2017, 10, 19, 11, 0), createDate(2017, 10, 22, 10, 0)));

		String url3 = "https://secure.booking.com/myreservations.html?aid=304142;label=gen173nr-1FCAEoggJCAlhYSDNiBW5vcmVmaIkBiAEBmAExuAEHyAEN2AEB6AEB-AECkgIBeagCAw;sid=14ee120a76a3c31b9149dba2bd41e231;auth_key=ogD9pO6HUWpk9rKN&";
		System.out.println(createAccommodation(createdTrip.getId(), "Hostal Mallqui", cuzco, url3,
				createDate(2017, 10, 25, 11, 0), createDate(2017, 10, 26, 10, 0)));

		// Flights
		String flightUrl = "https://www.onetwotrip.com/de/ticket/?number=H19771211&email=info@larsbremer.de";
		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-417", null, flightUrl,
				"Airbus A320", luxembourg, createDate(2017, 10, 17, 11, 30), london, createDate(2017, 10, 17, 12, 0)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-113", null, flightUrl, "Boeing 777",
				london, createDate(2017, 10, 17, 16, 5), newyork, createDate(2017, 10, 17, 19, 5)));

		System.out.println(createFlight(createdTrip.getId(), "American Airlines", "AA-7739", null, flightUrl,
				"Boeing 787-8", newyork, createDate(2017, 10, 17, 23, 25), lima, createDate(2017, 10, 18, 5, 55)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-5050", null, flightUrl,
				"Boeing 757", lima, createDate(2017, 11, 2, 6, 45), miami, createDate(2017, 11, 2, 13, 42)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-206", null, flightUrl,
				"Boeing 747-400", miami, createDate(2017, 11, 2, 18, 25), london, createDate(2017, 11, 3, 6, 45)));

		System.out.println(createFlight(createdTrip.getId(), "British Airways", "BA-416", null, flightUrl,
				"Airbus A320", london, createDate(2017, 11, 3, 8, 5), luxembourg, createDate(2017, 11, 3, 10, 25)));

		System.out.println(createFlight(createdTrip.getId(), "Avianca", "V54UL8", null, null, "Airbus A319", lima,
				createDate(2017, 10, 19, 14, 42), cuzco, createDate(2017, 10, 19, 16, 2)));

		System.out.println(createBusRide(createdTrip.getId(), "Turismomer", "Sun's Route", null, cuzco,
				createDate(2017, 10, 26, 7, 0), puno, createDate(2017, 10, 26, 17, 0)));

		System.out.println(
				createBusRide(createdTrip.getId(), "Cruz Del Sur", "Cruzero Evolution", "http://www.cruzdelsur.com.pe",
						arequipa, createDate(2017, 10, 29, 19, 0), paracas, createDate(2017, 10, 30, 7, 20)));

		System.out.println(
				createBusRide(createdTrip.getId(), "Cruz Del Sur", "Cruzero Plus", "http://www.cruzdelsur.com.pe",
						paracas, createDate(2017, 10, 31, 16, 10), lima, createDate(2017, 10, 31, 20, 5)));

		// Activity
		String note = "Inka Trail: Day 1";
		Activity createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 22, 00, 00),
				createDate(2017, 10, 22, 23, 59), note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 2";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 23, 12, 00), null, note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 3";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 24, 12, 00), null, note);
		System.out.println(createdActivity);

		note = "Inka Trail: Day 4";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 25, 12, 00), null, note);
		System.out.println(createdActivity);

		note = "Train back to Cuzco";
		createdActivity = createActivity(createdTrip.getId(), createDate(2017, 10, 25, 16, 00), null, note);
		System.out.println(createdActivity);

		System.out.println(createActivity(createdTrip.getId(), createDate(2017, 10, 31, 8, 00),
				createDate(2017, 10, 31, 10, 00), "Ballestas Islands Tour"));

	}

	private static Activity createActivity(String tripId, Calendar startDate, Calendar endDate, String note) {

		Activity activity = new Activity();
		activity.setStartDate(startDate);
		activity.setEndDate(endDate);
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

	private static Accommodation createAccommodation(String tripId, String name, Location location, String url,
			Calendar startDate, Calendar endDate) {

		Accommodation accommodation = new Accommodation();
		accommodation.setName(name);
		accommodation.setTripId(tripId);
		accommodation.setStartDate(startDate);
		accommodation.setEndDate(endDate);
		accommodation.setLocation(location);
		accommodation.setUrl(url);

		Accommodation createdAccommodation = tripController.createAccommodation(accommodation);
		return createdAccommodation;
	}

	// private static TrainRide createTrainRide(String tripId, String name, String
	// number, Location startLocation,
	// Calendar startDate, Location endLocation, Calendar endDate) {
	//
	// TrainRide trainRide = new TrainRide();
	// trainRide.setNumber(number);
	// trainRide.setName(name);
	// trainRide.setTripId(tripId);
	// trainRide.setStartDate(startDate);
	// trainRide.setDepartureLocation(startLocation);
	// trainRide.setEndDate(endDate);
	// trainRide.setArrivalLocation(endLocation);
	//
	// TrainRide createdTrainRide = tripController.createTrainRide(trainRide);
	// return createdTrainRide;
	// }

	private static BusRide createBusRide(String tripId, String name, String service, String url, Location startLocation,
			Calendar startDate, Location endLocation, Calendar endDate) {

		BusRide busRide = new BusRide();
		busRide.setService(service);
		busRide.setName(name);
		busRide.setTripId(tripId);
		busRide.setStartDate(startDate);
		busRide.setDepartureLocation(startLocation);
		busRide.setEndDate(endDate);
		busRide.setArrivalLocation(endLocation);
		busRide.setUrl(url);

		BusRide createdBusRide = tripController.createBus(busRide);
		return createdBusRide;
	}

	private static Flight createFlight(String tripId, String airline, String number, String seatNumber, String url,
			String plane, Location startLocation, Calendar startDate, Location endLocation, Calendar endDate) {

		Flight flight = new Flight();
		flight.setNumber(number);
		flight.setAirline(airline);
		flight.setTripId(tripId);
		flight.setStartDate(startDate);
		flight.setDepartureLocation(startLocation);
		flight.setEndDate(endDate);
		flight.setArrivalLocation(endLocation);
		flight.setSeatNumber(seatNumber);
		flight.setAirplane(plane);
		flight.setUrl(url);

		Flight createdFlight = tripController.createFlight(flight);
		return createdFlight;
	}
}
