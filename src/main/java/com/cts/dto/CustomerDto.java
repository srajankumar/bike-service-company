package com.cts.dto;

import com.cts.validation.Create;
import com.cts.validation.Update;

import jakarta.validation.constraints.NotBlank;
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
	
	// Customer name should contain only letters and spaces and cannot be empty
	@NotBlank(message = "Customer name is required", groups = Create.class)
	@Pattern(regexp = "^[A-Za-z ]+", message = "Customer name should contain only alphabets", groups = {Create.class, Update.class})
	private String customerName;
	
	// Phone number must be exactly 10 digits and cannot be empty
	@NotBlank(message = "Phone number is required", groups = Create.class)
	@Pattern(regexp = "\\d{10}", message = "Phone number should contain only 10 digits", groups = {Create.class, Update.class})
	private String phoneNumber; 
	
	@NotBlank(message = "House number is required", groups = Create.class)
	private String houseNo;
	
	@NotBlank(message = "Street is required", groups = Create.class)
	private String street;
	
	@NotBlank(message = "Landmark is required", groups = Create.class)
	private String landmark;
	
	@NotBlank(message = "City is required", groups = Create.class)
	private String city;
	
	@NotBlank(message = "State is required", groups = Create.class)
	private String state;
	
	// PIN code must be a valid 6-digit number and cannot be empty
	@NotBlank(message = "PIN code is required", groups = Create.class)
	@Pattern(regexp = "[1-9][0-9]{5}", message = "Invalid PIN code", groups = {Create.class, Update.class})
	private String pin;
	
}
