package com.cts.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.validation.Create;
import com.cts.validation.Update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Represents the details of a bike, including registration and customer info
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeDto {

	private long bikeId;
	
	@NotBlank(message = "Bike make is required", groups = Create.class)
	private String bikeMake;
	
	@NotBlank(message = "Model name is required", groups = Create.class)
	private String modelName;
	
	// Registration number must follow a specific format (e.g., KA19MA1234) and cannot be null or empty
	@NotBlank(message = "Bike registration number is required", groups = Create.class)
	@Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}", message = "Invalid registration number format", groups = {Create.class, Update.class})
	private String bikeRegistrationNumber;
	
	// Chassis number must be exactly 17 digits and cannot be null or empty
	@NotBlank(message = "Bike chassis number is required", groups = Create.class)
	@Pattern(regexp = "\\d{17}", message = "Bike chassis number must be 17 digits", groups = {Create.class, Update.class})
	private String bikeChassisNumber;
	
	@NotBlank(message = "Known issues field is required", groups = Create.class)
	private String knownIssues;
	
	// Cost of service must be a positive number
	@Positive(message = "Cost must be a positive number", groups = {Create.class, Update.class})
	private double cost;
	
	@NotNull(message = "Given date field is required", groups = Create.class)
	private LocalDateTime givenDate;
	
	@NotNull(message = "Expected delivery date field is required", groups = Create.class)
	private LocalDate expectedDeliveryDate;
	
	private LocalDateTime createdDateAndTime;
	private LocalDateTime updatedDateAndTime;
	
	// Customer details associated with the bike
	@Valid
	private CustomerDto customer;
	
}
