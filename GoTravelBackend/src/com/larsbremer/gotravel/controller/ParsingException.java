package com.larsbremer.gotravel.controller;

public class ParsingException extends RuntimeException {

	private static final long serialVersionUID = 3435648246723717172L;

	public ParsingException(Exception e) {
		super(e);
	}
}
