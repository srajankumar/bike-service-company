package com.cts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Represents customer details stored in the database
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
