package com.larsbremer.gotravel.controller;

import java.util.Calendar;
import java.util.TimeZone;

import com.larsbremer.gotravel.model.Segment;

public class SegmentController {

	public static Calendar getEndOfDay(Calendar date) {

		Calendar endOfDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		endOfDay.setTime(date.getTime());
		endOfDay.set(Calendar.HOUR_OF_DAY, 23);
		endOfDay.set(Calendar.MINUTE, 59);
		endOfDay.set(Calendar.SECOND, 59);

		return endOfDay;
	}

	public static boolean isEndOfDay(Calendar date) {

		Calendar endOfDay = getEndOfDay(date);
		return endOfDay.equals(date);
	}

	public static Calendar getBeginningOfDay(Calendar date) {

		Calendar beginningOfDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		beginningOfDay.setTime(date.getTime());
		beginningOfDay.set(Calendar.HOUR_OF_DAY, 0);
		beginningOfDay.set(Calendar.MINUTE, 0);
		beginningOfDay.set(Calendar.SECOND, 0);

		return beginningOfDay;
	}

	public static boolean isBeginningOfDay(Calendar date) {

		Calendar beginningOfDay = getEndOfDay(date);
		return beginningOfDay.equals(date);
	}

	public static boolean segmentsFollowEachOther(Segment s1, Segment s2) {

		Calendar s1EndDate = s1.getEndDate();
		Calendar s2StartDate = s2.getStartDate();

		s1EndDate.add(Calendar.SECOND, 1);

		return s1EndDate.equals(s2StartDate);
	}

	public static boolean doSegmentsOverlap(Segment s1, Segment s2) {

		if (s1.getStartDate().before(s2.getEndDate()) && s2.getStartDate().before(s1.getEndDate())) {
			return true;
		}

		return false;
	}

}
