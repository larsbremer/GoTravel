package com.larsbremer.blueprint.model;

import java.util.Calendar;

public abstract class Segment {

	private String id;
	private Calendar startDate;
	private Calendar endDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return this.getClass().getName().toLowerCase();
	}
}
