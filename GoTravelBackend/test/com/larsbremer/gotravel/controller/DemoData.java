package com.larsbremer.gotravel.controller;

import com.larsbremer.gotravel.model.Trip;

public class DemoData {

	public static void main(String[] args) {
		createDemoData();
	}

	private static void createDemoData() {

		TripController tripController = new TripController();

		tripController.createTrip(new Trip());
	}

}
