package com.larsbremer.gotravel.model;

public class Location {

	private String city;
	private String country;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Location: [city: " + city + "], [country: " + country + "]";
	}

}
