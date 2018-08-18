package com.larsbremer.gotravel.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.larsbremer.gotravel.controller.TripController;
import com.larsbremer.gotravel.model.Trip;

import io.swagger.annotations.Api;

@Path("/trips")
@Api(value = "/trips")
public class RestTrip {

	private static final Logger logger = LogManager.getLogger(RestTrip.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveTrips() {

		try {
			List<Trip> trips = new TripController().getTrips();
			return Response.ok(trips).build();

		} catch (Exception e) {
			logger.error("Error retrieving trips", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@GET
	@Path("/{trip-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveTrip(@PathParam("trip-id") String tripId) {

		try {
			Trip trip = new TripController().getTrip(tripId, true);
			return Response.ok(trip).build();

		} catch (Exception e) {
			logger.error("Error retrieving trip", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}
}