package com.larsbremer.gotravel.db.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Accomodation;
import com.larsbremer.gotravel.model.Location;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoAccomodation {

	private Accomodation parseAccomodationFields(Document doc) {

		Accomodation accomodation = new Accomodation();

		accomodation.setTripId(doc.getString("tripId"));
		accomodation.setName(doc.getString("name"));

		Document locationDoc = (Document) doc.get("location");
		Location location = MongoLocation.parseLocationFields(locationDoc);
		accomodation.setLocation(location);

		MongoSegment.addSegmentFields(accomodation, doc);

		return accomodation;
	}

	public List<Accomodation> searchAccomodations(Accomodation filter, Integer offset, Integer size)
			throws JsonProcessingException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.ACCOMODATION);

		FindIterable<Document> search = collection.find();

		Bson bsonFilter = MongoParser.convertPojoToBson(filter);
		if (filter != null) {
			search.filter(bsonFilter);
		}

		if (offset != null && offset > 0) {
			search.skip(offset);
		}

		if (size != null && size > 0) {
			search.limit(size);
		}

		List<Accomodation> accomodations = new ArrayList<>();
		for (Document doc : search) {
			accomodations.add(parseAccomodationFields(doc));
		}

		return accomodations;
	}

}
