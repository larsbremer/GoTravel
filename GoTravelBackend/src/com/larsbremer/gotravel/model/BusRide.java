package com.larsbremer.gotravel.model;

public class BusRide extends Transportation {

	private String service;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "Bus Ride: [name: " + name + "], [service: " + service + "], " + super.toString();
	}

}
