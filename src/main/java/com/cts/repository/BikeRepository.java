package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Bike;

// Handles database operations for the Bike entity
public interface BikeRepository extends JpaRepository<Bike, Long> {

}
