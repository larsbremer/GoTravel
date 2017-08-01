package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.larsbremer.gotravel.db.mongo.MongoConnection.Collection;
import com.larsbremer.gotravel.model.Activity;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoActivity {

	public List<Activity> search(Activity filter, Integer offset, Integer size) throws IOException, ParseException {

		MongoCollection<Document> collection = MongoConnection.getDatabaseCollection(Collection.ACTIVITY);

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

		List<Activity> activities = new ArrayList<>();
		for (Document doc : search) {
			Activity activity = MongoParser.getObject(doc, Activity.class);
			activities.add(activity);
		}

		return activities;
	}

}
