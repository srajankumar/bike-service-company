package com.cts.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bikes")
public class Bike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bikeId;
	private String bikeMake;
	private String modelName;
	@Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}", message = "Invalid registration number format.")
	private String bikeRegistrationNumber;
	@Pattern(regexp = "\\d{17}", message = "Bike chassis number must be 17 digits.")
	private String bikeChassisNumber;
	private String knownIssues;
	@Positive(message = "Cost must be a positive number.")
	private double cost;
	private LocalDateTime givenDate;
	private LocalDate expectedDeliveryDate;
	private LocalDateTime CreatedDateAndTime;
	private LocalDateTime updatedDateAndTime;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
