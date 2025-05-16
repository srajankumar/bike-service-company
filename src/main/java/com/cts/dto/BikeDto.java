package com.cts.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.entities.Customer;

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
	private String bikeRegistrationNumber;
	private String bikeChassisNumber;
	private String knownIssues;
	private double cost;
	private LocalDateTime givenDate;
	private LocalDate expectedDeliveryDate;
	private LocalDateTime CreatedDateAndTime;
	private LocalDateTime updatedDateAndTime;
	private Customer customer;
	
}
