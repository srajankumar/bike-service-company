package com.cts.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

	private long customerId;
	@Pattern(regexp = "^[A-Za-z ]+", message = "Customer name should contain only alphabets")
	private String customerName;
	@Pattern(regexp = "\\d{10}", message = "Phone number should contain only 10 digits")
	private String phoneNumber; 
	private String houseNo;
	private String street;
	private String landmark;
	private String city;
	private String state;
	private String pin;
	
}
