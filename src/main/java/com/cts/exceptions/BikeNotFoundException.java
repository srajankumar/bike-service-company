package com.cts.exceptions;

// Custom exception for handling cases where a bike is not found
public class BikeNotFoundException extends RuntimeException {

	// Default constructor with no message
	public BikeNotFoundException() {
		super();
	}

	// Constructor with a custom error message
	public BikeNotFoundException(String message) {
		super(message);
	}	
}
