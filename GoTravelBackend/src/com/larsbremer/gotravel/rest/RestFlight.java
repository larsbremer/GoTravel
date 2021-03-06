package com.larsbremer.gotravel.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.larsbremer.gotravel.controller.TripController;
import com.larsbremer.gotravel.model.Flight;

import io.swagger.annotations.Api;

@Path("/flights")
@Api(value = "/flights")
public class RestFlight {

	private static final Logger logger = LogManager.getLogger(RestFlight.class);

	@GET
	@Path("/{flight-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveFlight(@PathParam("flight-id") String flightId) {

		try {
			Flight flight = new TripController().getFlight(flightId);
			return Response.ok(flight).build();

		} catch (Exception e) {
			logger.error("Error retrieving flight", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@PUT
	@Path("/{flight-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFlight(@PathParam("flight-id") String flightId, Flight flight) {

		try {
			Flight updatedFlight = new TripController().updateFlight(flightId, flight);
			return Response.ok(updatedFlight).build();

		} catch (Exception e) {
			logger.error("Error: ", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

}