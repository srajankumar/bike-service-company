package com.cts.service;

import com.cts.dto.LoginDto;

// Defines authentication-related operations for the application
public interface AuthService {

	String login(LoginDto loginDto);
}
