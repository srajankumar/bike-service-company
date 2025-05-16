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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;
	@Pattern(regexp = "[A-Za-z\s]+", message = "Customer name should contain only alphabets.")
	private String customerName;
	@Pattern(regexp = "\\d{10}", message = "Phone number should contain only 10 digits.")
	private String phoneNumber; 
	private String houseNo;
	private String street;
	private String landmark;
	private String city;
	private String state;
	private String pin;
	
}
