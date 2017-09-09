package com.larsbremer.gotravel.model;

public class Flight extends Transportation {

	private String number;
	private String airline;
	private String seatNumber;
	private String airplane;

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

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}

	public String getAirplane() {
		return airplane;
	}

	@Override
	public String toString() {
		return "Flight: [number: " + number + "] " + super.toString();
	}
}
