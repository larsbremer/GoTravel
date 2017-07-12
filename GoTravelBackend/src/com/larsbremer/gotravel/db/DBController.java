package com.larsbremer.gotravel.db;

import java.util.List;

import com.larsbremer.gotravel.model.Trip;

public interface DBController extends AutoCloseable {

	List<Trip> searchTrip(Trip filter, Integer offset, Integer size);

}
