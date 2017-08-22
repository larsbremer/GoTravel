package com.larsbremer.gotravel.model;

public class Flight extends Transportation {

	private String number;
	private String airline;

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Flight: [number: " + number + "] " + super.toString();
	}

}
