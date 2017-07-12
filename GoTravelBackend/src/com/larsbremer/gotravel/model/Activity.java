package com.larsbremer.gotravel.model;

public class Activity extends Segment {

	private String note;
	private String tripId;

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
