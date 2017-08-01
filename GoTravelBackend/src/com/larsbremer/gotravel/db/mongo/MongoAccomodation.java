package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Accomodation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoAccomodation {

	public List<Accomodation> search(Accomodation filter, Integer offset, Integer size)
			throws IOException, ParseException {

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
			Accomodation accomodation = MongoParser.getObject(doc, Accomodation.class);
			accomodations.add(accomodation);
		}

		return accomodations;
	}

	public Accomodation create(Accomodation accomodation) throws IOException, ParseException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.ACCOMODATION);

		Document doc = MongoParser.convertPojoToDocument(accomodation);
		collection.insertOne(doc);

		return MongoParser.getObject(doc, Accomodation.class);
	}

}
