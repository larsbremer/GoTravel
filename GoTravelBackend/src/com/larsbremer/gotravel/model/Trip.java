package com.larsbremer.gotravel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Trip extends Segment {

	private String name;
	private PriorityQueue<Segment> segments = new PriorityQueue<>(new SegmentComparator());

	public String getName() {

		return name;
	}

	public List<Segment> getSegments() {
		return new ArrayList<>(segments);
	}

	public void addSegment(Segment segment) {
		this.segments.add(segment);
	}

	public void setName(String name) {
		this.name = name;
	}

	class SegmentComparator implements Comparator<Segment> {

		@Override
		public int compare(Segment o1, Segment o2) {

			Calendar o1StartDate = o1.getStartDate();
			Calendar o1EndDate = o1.getEndDate();

			Calendar o2StartDate = o2.getStartDate();
			Calendar o2EndDate = o2.getEndDate();

			if (o1EndDate.before(o2StartDate)) {
				return -1;
			}

			if (o2EndDate.after(o1StartDate)) {
				return 1;
			}

			throw new IllegalStateException();
		}

	}
}
