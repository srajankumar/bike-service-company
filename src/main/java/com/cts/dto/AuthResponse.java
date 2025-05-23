package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Represents the response sent after a user logs in
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {

	private String jwtTokens;
	private String type="Bearer";
}
