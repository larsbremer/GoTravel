package com.larsbremer.gotravel.model;

public class TrainRide extends Transportation {

	private String number;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Train Ride: [name: " + name + "], [number: " + number + "] " + super.toString();
	}

}
