package com.cts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Bike;

// Handles H2 database operations for the Bike entity
public interface BikeTestRepository extends JpaRepository<Bike, Long> {

}
