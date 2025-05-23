package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Customer;

// Handles database operations for the Customer entity
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
