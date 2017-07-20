package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larsbremer.gotravel.model.Segment;
import com.mongodb.BasicDBObject;

public class MongoParser {

	private static final String MONGO_ID = "_id";

	public static Bson convertPojoToBson(Object obj) throws JsonProcessingException {

		String jsonString = parsePojoToString(obj);

		BasicDBObject bsonFilter = BasicDBObject.parse(jsonString);

		if (obj instanceof Segment) {
			convertToMongoId(obj, bsonFilter);
			bsonFilter.remove("type");
		}

		return bsonFilter;
	}

	public static Document convertPojoToDocument(Object obj) throws JsonProcessingException {

		String jsonString = parsePojoToString(obj);

		Document doc = Document.parse(jsonString);

		if (obj instanceof Segment) {
			convertToMongoId(obj, doc);
			doc.remove("type");
		}

		return doc;
	}

	private static void convertToMongoId(Object obj, Map<String, Object> bsonFilter) {
		String pojoId = ((Segment) obj).getId();

		if (pojoId != null) {
			ObjectId mongoId = new ObjectId(pojoId);
			bsonFilter.put(MONGO_ID, mongoId);
			bsonFilter.remove("id");
		}
	}

	private static String parsePojoToString(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		String jsonString = mapper.writeValueAsString(obj);

		return jsonString;
	}

	public static <T> T getObject(Document doc, Class<T> targetType) throws JsonProcessingException, IOException {

		if (doc.containsKey(MONGO_ID)) {
			String mongoId = doc.getObjectId(MONGO_ID).toHexString();
			doc.append("id", mongoId);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.readValue(doc.toJson(), targetType);
	}

}
