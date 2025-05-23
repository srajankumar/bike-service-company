package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Holds login details for user authentication
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

	private String usernameOrEmail;
	private String password;
}
