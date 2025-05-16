package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

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
