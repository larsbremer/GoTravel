package com.larsbremer.gotravel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class Trip extends Segment {

	private String name;
	private List<Segment> segments = new ArrayList<>();

	public String getName() {

		return name;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void addSegment(Segment segment) {
		this.segments.add(segment);
		this.segments.sort(new SegmentComparator());
	}

	public void setName(String name) {
		this.name = name;
	}

	class SegmentComparator implements Comparator<Segment> {

		@Override
		public int compare(Segment o1, Segment o2) {

			Calendar o1EndDate = o1.getEndDate();
			Calendar o2EndDate = o2.getEndDate();

			if (o1EndDate.before(o2EndDate)) {
				return -1;
			}

			if (o2EndDate.before(o1EndDate)) {
				return 1;
			}

			throw new IllegalStateException("Two segments overlap: " + o1 + " and " + o2);
		}

	}

	@Override
	public String toString() {
		return "Trip: [name: " + name + "]" + super.toString();
	}
}
