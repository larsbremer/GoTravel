package com.larsbremer.blueprint.db;

import java.util.List;

import com.larsbremer.blueprint.model.Trip;

public interface DBController extends AutoCloseable {

	List<Trip> searchTrip(Trip filter, Integer offset, Integer size);

}
