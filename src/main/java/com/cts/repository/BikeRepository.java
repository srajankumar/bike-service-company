package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {

}
