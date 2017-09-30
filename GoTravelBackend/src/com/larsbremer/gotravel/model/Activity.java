package com.larsbremer.gotravel.model;

public class Activity extends Segment {

	private String tripId;

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	@Override
	public String toString() {

		return "Activity: " + super.toString();
	}
}
