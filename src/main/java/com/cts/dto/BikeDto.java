package com.cts.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.entities.Customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeDto {

	private long bikeId;
	private String bikeMake;
	private String modelName;
	@Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}", message = "Invalid registration number format")
	private String bikeRegistrationNumber;
	@Pattern(regexp = "\\d{17}", message = "Bike chassis number must be 17 digits")
	private String bikeChassisNumber;
	private String knownIssues;
	@Positive(message = "Cost must be a positive number")
	private double cost;
	private LocalDateTime givenDate;
	private LocalDate expectedDeliveryDate;
	private LocalDateTime CreatedDateAndTime;
	private LocalDateTime updatedDateAndTime;
	@Valid
	private Customer customer;
	
}
