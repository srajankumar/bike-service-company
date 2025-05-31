package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.AuthResponse;
import com.cts.dto.LoginDto;
import com.cts.service.AuthService;

// Handles authentication requests for the application
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	// Logs in a user and returns a JWT token
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
		
		var result = authService.login(loginDto); // Calls the service to validate login
		AuthResponse response = new AuthResponse();
		response.setJwtTokens(result); // Stores the generated token in the response
		return ResponseEntity.ok(response); // Sends back the token in the HTTP response
	}
	
}
