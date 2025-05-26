package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entities.Role;

// Handles database operations for the Role entity
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
