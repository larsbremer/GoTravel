package com.larsbremer.gotravel.model;

import java.util.Calendar;

public class Activity extends DatabaseItem {

	private String note;
	private String tripId;
	private Calendar date;

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

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

	@Override
	public String toString() {

		String dateString = "n/a";
		if (this.date != null) {
			dateString = getDate().getTime().toString();
		}

		return "Activity: [note: " + note + "],  [date: " + dateString + "]" + super.toString();
	}
}
