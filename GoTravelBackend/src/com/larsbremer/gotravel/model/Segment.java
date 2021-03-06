package com.larsbremer.gotravel.model;

import java.util.Calendar;

public abstract class Segment extends DatabaseItem {

	private Calendar startDate;
	private Calendar endDate;
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public String toString() {

		String startDateString = "n/a";
		if (this.startDate != null) {
			startDateString = getStartDate().getTime().toString();
		}

		String endDateString = "n/a";
		if (this.endDate != null) {
			endDateString = getEndDate().getTime().toString();
		}

		return ", [time: " + startDateString + " - " + endDateString + "]" + super.toString();
	}
}
