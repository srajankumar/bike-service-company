package com.cts.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Stores customer details for the application
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

	private long customerId;
	
	// Customer name should contain only letters and spaces
	@Pattern(regexp = "^[A-Za-z ]+", message = "Customer name should contain only alphabets")
	private String customerName;
	
	// Phone number must be exactly 10 digits
	@Pattern(regexp = "\\d{10}", message = "Phone number should contain only 10 digits")
	private String phoneNumber; 
	
	private String houseNo;
	private String street;
	private String landmark;
	private String city;
	private String state;
	
	// PIN code must be a valid 6-digit number
	@Pattern(regexp = "[1-9][0-9]{5}", message = "Invalid PIN code")
	private String pin;
	
}
