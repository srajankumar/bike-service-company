package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.User;

// Handles database operations for the User entity
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String eail);
	boolean existsByUsernameOrEmail(String username, String email);

}
