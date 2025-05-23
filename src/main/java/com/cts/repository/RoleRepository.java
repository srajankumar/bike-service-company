package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Role;

// Handles database operations for the Role entity
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String name);
	boolean existsByName(String name);
		
}
