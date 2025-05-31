package com.cts.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Custom exception for handling errors in the Bike Service application
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeServiceException extends RuntimeException {

	private HttpStatus status;
	private String message;
}
