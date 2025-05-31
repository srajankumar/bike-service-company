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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Represents a bike and its details stored in the database
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
	private String bikeRegistrationNumber;
	private String bikeChassisNumber;
	private String knownIssues;
	private double cost;
	private LocalDateTime givenDate;
	private LocalDate expectedDeliveryDate;
	private LocalDateTime createdDateAndTime;
	private LocalDateTime updatedDateAndTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
