package com.larsbremer.gotravel.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.larsbremer.gotravel.controller.TripController;
import com.larsbremer.gotravel.model.Trip;

import io.swagger.annotations.Api;

@Path("/trips")
@Api(value = "/trips")
public class RestTrip {

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