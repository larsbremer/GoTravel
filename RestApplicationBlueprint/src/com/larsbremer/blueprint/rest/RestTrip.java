package com.larsbremer.blueprint.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.larsbremer.blueprint.db.DBConnection;
import com.larsbremer.blueprint.db.DBController;
import com.larsbremer.blueprint.model.Trip;

import io.swagger.annotations.Api;

@Path("/trips")
@Api(value = "/trips")
public class RestTrip {

	private static final Logger logger = LogManager.getLogger(RestTrip.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveTrips() {

		try (DBController dbController = DBConnection.getDatabaseController()) {

			List<Trip> trips = dbController.searchTrip(null, null, null);
			return Response.ok(trips).build();

		} catch (Exception e) {
			logger.error("Error retrieving trips.", e);
			return Response.serverError().build();
		}
	}

}