package com.cts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private String customerName;
	private String phoneNumber; 
	private String houseNo;
	private String street;
	private String landmark;
	private String city;
	private String state;
	private String pin;
	
}
