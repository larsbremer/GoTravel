package com.larsbremer.gotravel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.larsbremer.gotravel.controller.SegmentController;

public class DateSegment extends Segment {

	private Accommodation eveningAccommodation;
	private String tripId;
	private List<Activity> activities = new ArrayList<>();

	public DateSegment() {

	}

	public DateSegment(Calendar cal) {

		Calendar beginningOfDay = SegmentController.getBeginningOfDay(cal);
		this.setStartDate(beginningOfDay);

		Calendar endOfDay = SegmentController.getEndOfDay(cal);
		this.setStartDate(endOfDay);
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Accommodation getEveningAccommodation() {
		return eveningAccommodation;
	}

	public void setEveningAccommodation(Accommodation eveningAccommodation) {
		this.eveningAccommodation = eveningAccommodation;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	@Override
	public String toString() {
		return "DateSegment: " + super.toString();
	}

}
