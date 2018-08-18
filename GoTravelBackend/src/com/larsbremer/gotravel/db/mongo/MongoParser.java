package com.larsbremer.gotravel.db.mongo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larsbremer.gotravel.model.DatabaseItem;
import com.mongodb.BasicDBObject;

public class MongoParser {

	private static final String MONGO_ID = "_id";
	private static final String DATE_PREFIX = "#date:";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("'" + DATE_PREFIX + "'yyyy-MM-dd'T'HH:mm:ssZ");

	public static Bson convertPojoToBson(Object obj) throws JsonProcessingException {

		String jsonString = parsePojoToString(obj);

		BasicDBObject bsonFilter = BasicDBObject.parse(jsonString);

		if (obj instanceof DatabaseItem) {
			convertToMongoId(obj, bsonFilter);
			bsonFilter.remove("type");
		}

		return bsonFilter;
	}

	public static Document convertPojoToDocument(Object obj) throws JsonProcessingException, ParseException {

		String jsonString = parsePojoToString(obj);

		Document doc = Document.parse(jsonString);

		convertDatePlaceholdersToDate(doc);

		if (obj instanceof DatabaseItem) {
			convertToMongoId(obj, doc);
			doc.remove("type");
		}

		return doc;
	}

	private static void convertDatePlaceholdersToDate(Document doc) throws ParseException {
		for (String key : new ArrayList<>(doc.keySet())) {

			Object value = doc.get(key);

			if (!(value instanceof String)) {
				continue;
			}

			String stringValue = (String) value;
			if (stringValue.startsWith(DATE_PREFIX)) {

				Date date = sdf.parse(stringValue);

				doc.remove(key);
				doc.append(key, date);
			}
		}
	}

	private static void convertDatesToDatePlaceholders(Document doc) throws ParseException {

		for (String key : new ArrayList<>(doc.keySet())) {

			Object value = doc.get(key);

			if (value instanceof Date) {

				String dateString = sdf.format((Date) value);

				doc.remove(key);
				doc.append(key, dateString);
			}
		}
	}

	private static void convertToMongoId(Object obj, Map<String, Object> bsonFilter) {
		String pojoId = ((DatabaseItem) obj).getId();

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

		mapper.setDateFormat(sdf);

		String jsonString = mapper.writeValueAsString(obj);

		return jsonString;
	}

	public static <T> T getObject(Document doc, Class<T> targetType)
			throws JsonProcessingException, IOException, ParseException {

		if (doc.containsKey(MONGO_ID)) {
			String mongoId = doc.getObjectId(MONGO_ID).toHexString();
			doc.append("id", mongoId);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		mapper.setDateFormat(sdf);

		convertDatesToDatePlaceholders(doc);

		String json = doc.toJson();

		return mapper.readValue(json, targetType);
	}

}
