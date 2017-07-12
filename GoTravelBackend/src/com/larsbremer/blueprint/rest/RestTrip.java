package com.larsbremer.blueprint.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.larsbremer.blueprint.controller.TripController;
import com.larsbremer.blueprint.model.Trip;

import io.swagger.annotations.Api;

@Path("/trips")
@Api(value = "/trips")
public class RestTrip {

	private static final Logger logger = LogManager.getLogger(RestTrip.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveTrips() {

		try {
			List<Trip> trips = new TripController().getTrips(null);
			return Response.ok(trips).build();

		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

	}
}