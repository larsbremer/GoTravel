package com.larsbremer.gotravel.db.mongo;

import java.util.Calendar;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.larsbremer.gotravel.model.Segment;
import com.mongodb.BasicDBObject;

public class MongoSegment {

	private static final String MONGO_ID = "_id";

	static Bson addSegmentFieldsToFilter(Segment filter, BasicDBObject bsonFilter) {

		if (filter.getId() != null) {
			bsonFilter.put(MONGO_ID, new ObjectId(filter.getId()));
		}

		return bsonFilter;
	}

	static Segment addSegmentFields(Segment segment, Document doc) {

		segment.setId(doc.getObjectId(MONGO_ID).toHexString());

		if (doc.containsKey("startDate")) {
			Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			c1.setTimeInMillis(doc.getLong("startDate"));
			segment.setStartDate(c1);
		}

		if (doc.containsKey("endDate")) {
			Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			c2.setTimeInMillis(doc.getLong("endDate"));
			segment.setEndDate(c2);
		}

		return segment;
	}

}
