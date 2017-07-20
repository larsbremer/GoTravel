package com.larsbremer.gotravel.db.mongo;

import org.bson.Document;

import com.larsbremer.gotravel.model.Location;

public class MongoLocation {

	static Location parseLocationFields(Document doc) {

		Location location = new Location();

		location.setCity(doc.getString("city"));
		location.setCountry(doc.getString("country"));

		return location;
	}

}
