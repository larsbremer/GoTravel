package com.larsbremer.gotravel.model;

public class DatabaseItem {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {

		return ", [id: " + id + "]";
	}
}
