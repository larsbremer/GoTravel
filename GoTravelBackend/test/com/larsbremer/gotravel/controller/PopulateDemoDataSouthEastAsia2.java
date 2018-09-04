package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.larsbremer.gotravel.model.Accommodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.Location;
import com.larsbremer.gotravel.model.TrainRide;
import com.larsbremer.gotravel.model.Trip;

public class PopulateDemoDataSouthEastAsia2 {

	private static TripController tripController = new TripController();

	public static void main(String[] args) {
		createDemoData();
	}

	private static void createDemoData() {

		// Trip
		Trip createdTrip = createTrip(createDate(2018, 10, 12, 11, 00), createDate(2018, 11, 2, 19, 5));
		System.out.println(createdTrip);

		// Location
		Location paris = createLocation("Paris", "France");
		Location luxembourg = createLocation("Luxembourg", "Luxembourg");
		Location amsterdam = createLocation("Amsterdam", "Netherlands");
		Location hongkong = createLocation("Hong Kong", "Hong Kong");
		Location bangkok = createLocation("Bangkok", "Thailand");
		Location thanaleng = createLocation("Thanaleng", "Laos");
		Location nongkhai = createLocation("Nong Khai", "Thailand");
		Location siemriep = createLocation("Siem Riep", "Cambodia");
		Location vientiane = createLocation("Vientiane", "Laos");
		Location vangveng = createLocation("Vang Veng", "Laos");
		Location luangprabang = createLocation("Luang Prabang", "Laos");
		Location thakhek = createLocation("Thakhek", "Laos");
		Location dondet = createLocation("Don Det", "Laos");

		// Acccommodation
//		String url = "https://secure.booking.com/myreservations.html?aid=304142;label=gen173nr-1DCAEoggJCAlhYSDNiBW5vcmVmaIkBiAEBmAEHuAEHyAEN2AED6AEBkgIBeagCAw;sid=843d82863c6586b1e75a5583e8cb45c2;auth_key=r5UBuUl3VYXp2wDF&";
//		Accommodation createdAccommodation = createAccommodation(createdTrip.getId(), "Pucllana Lodge", lima, url,
//				createDate(2017, 10, 18, 13, 0), createDate(2017, 10, 19, 12, 0));
//		System.out.println(createdAccommodation);

		// Flights
		System.out.println(createFlight(createdTrip.getId(), "Luxair", "AF-4603", null, null, "DHC 8-400 Dash 8",
				luxembourg, createDate(2018, 10, 12, 11, 00), paris, createDate(2018, 10, 12, 12, 0)));

		System.out.println(createFlight(createdTrip.getId(), "Air France", "AF-0166", null, null, "Boeing 777-200",
				paris, createDate(2018, 10, 12, 16, 05), bangkok, createDate(2018, 10, 13, 8, 20)));

		System.out.println(createFlight(createdTrip.getId(), "Bangkok Airways", null, null, null, null, bangkok,
				createDate(2018, 10, 13, 14, 40), luangprabang, createDate(2018, 10, 13, 16, 50)));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, 14, 8, 0),
				createDate(2018, 10, 14, 22, 00), "Relaxing."));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, 15, 8, 30),
				createDate(2018, 10, 15, 19, 00), "Kayaking to Pak Ou Caves (greendiscoverylaos.com)"));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, 16, 8, 0),
				createDate(2018, 10, 16, 14, 00), "Excursion to Tat Kuang Si Waterfalls"));

		System.out.println(createBusRide(createdTrip.getId(), null, null, null, luangprabang,
				createDate(2018, 10, 17, 9, 0), vientiane, createDate(2018, 10, 17, 19, 0)));

		System.out.println(createBusRide(createdTrip.getId(), null, null, null, vientiane,
				createDate(2018, 10, 19, 8, 0), thakhek, createDate(2018, 10, 19, 12, 0)));

		int theloopDay1 = 20;
		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, theloopDay1, 8, 0),
				createDate(2018, 10, theloopDay1, 20, 00), "The Loop Day 1"));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, theloopDay1 + 1, 8, 0),
				createDate(2018, 10, theloopDay1 + 1, 20, 00), "The Loop Day 2"));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, theloopDay1 + 2, 8, 0),
				createDate(2018, 10, theloopDay1 + 2, 20, 00), "The Loop Day 3"));

		System.out.println(createBusRide(createdTrip.getId(), null, null, null, thakhek, createDate(2018, 10, 23, 8, 0),
				dondet, createDate(2018, 10, 23, 19, 0)));

		System.out.println(createBusRide(createdTrip.getId(), null, null, null, dondet, createDate(2018, 10, 26, 8, 0),
				siemriep, createDate(2018, 10, 26, 19, 0)));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, 27, 8, 0),
				createDate(2018, 10, 27, 8, 00), "Angkor"));

		System.out.println(createActivity(createdTrip.getId(), createDate(2018, 10, 28, 8, 0),
				createDate(2018, 10, 28, 20, 00), "Phyllis Flight to HK"));

		System.out.println(createFlight(createdTrip.getId(), "Hong Kong Express", null, null, null, null, siemriep,
				createDate(2018, 10, 29, 11, 15), hongkong, createDate(2018, 10, 29, 14, 40)));

		System.out.println(createFlight(createdTrip.getId(), "KLM", "KL-0888", null, null, "Boeing 747-400", hongkong,
				createDate(2018, 11, 2, 13, 35), amsterdam, createDate(2018, 11, 2, 19, 5)));

		System.out.println(createFlight(createdTrip.getId(), "KLM Cityhopper", "KL-1749", null, null, "Embraer 175",
				amsterdam, createDate(2018, 11, 2, 21, 20), luxembourg, createDate(2018, 11, 2, 22, 20)));

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
		trip.setName("Laos/Cambodia Trip");
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
