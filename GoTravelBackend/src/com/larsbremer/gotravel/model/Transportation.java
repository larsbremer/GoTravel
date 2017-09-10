package com.larsbremer.gotravel.model;

public class Transportation extends Segment {

	private String tripId;
	private Location departureLocation;
	private Location arrivalLocation;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Location getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(Location departureLocation) {
		this.departureLocation = departureLocation;
	}

	public Location getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(Location arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	@Override
	public String toString() {
		return "Transportation: [departure: " + departureLocation + "], [arrival: " + arrivalLocation + "]"
				+ super.toString();
	}

}
