package com.larsbremer.gotravel.db;

import java.util.List;

import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Activity;
import com.larsbremer.gotravel.model.BusRide;
import com.larsbremer.gotravel.model.Flight;
import com.larsbremer.gotravel.model.TrainRide;
import com.larsbremer.gotravel.model.Trip;

public interface DBController {

	List<Trip> searchTrips(Trip filter, Integer offset, Integer size);

	Trip searchTrip(Trip filter);

	Trip createTrip(Trip trip);

	List<Flight> searchFlights(Flight flightFilter, Integer offset, Integer size);

	List<Accomodation> searchAccomodations(Accomodation accomodationFilter, Integer offset, Integer size);

	List<Activity> searchActivities(Activity activityFilter, Integer offset, Integer size);

	Accomodation createAccomodation(Accomodation accomodation);

	Flight createFlight(Flight flight);

	Activity createActivity(Activity activity);

	TrainRide createTrainRide(TrainRide transportation);

	List<TrainRide> searchTrainRides(TrainRide trainRideFilter, Integer offset, Integer size);

	BusRide createBusRide(BusRide busRide);

	List<BusRide> searchBusRides(BusRide busRideFilter, Integer offset, Integer size);

}
