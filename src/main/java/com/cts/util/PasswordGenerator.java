package com.cts.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Utility class for generating encrypted passwords using BCrypt
public class PasswordGenerator {

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		System.out.println("password: " + encoder.encode("password"));
	
	}

}
