package com.cts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.BikeDto;
import com.cts.entities.Bike;
import com.cts.service.BikeService;
import com.cts.validation.Create;
import com.cts.validation.Update;


// Handles bike-related operations like getting, adding, updating, and deleting bikes
@RestController
@RequestMapping("/api/bikes")
public class BikeController {

	private BikeService bikeService;

	// Constructor injections
	public BikeController(BikeService bikeService) {
		super();
		this.bikeService = bikeService;
	}

	// Get all bike details
	@CrossOrigin(origins = "*")
	@GetMapping
	public List<BikeDto> getAll() {
		return bikeService.getAll();
	}
	
	// Get existing bike details by ID
	@GetMapping("/{id}")
	public BikeDto getById(@PathVariable long id) {
		return bikeService.getById(id);
	}

	// Save new bike details
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<Bike> addBike(@Validated(Create.class) @RequestBody BikeDto bikeDto) {
	    if (bikeDto == null || bikeDto.getCustomer() == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    Bike savedBike = bikeService.addBike(bikeDto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedBike); // Returns 201 Created
	}

	// Update existing bike details
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Bike> updateBike(@PathVariable long id, @Validated(Update.class) @RequestBody BikeDto bikeDto) {
	    if (bikeDto == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    Bike updatedBike = bikeService.updateBike(id, bikeDto);

	    return ResponseEntity.status(HttpStatus.CREATED).body(updatedBike); // Returns 201 Created
	}

	// Delete bike details
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBike(@PathVariable long id) {
	    bikeService.deleteBike(id);
	    return ResponseEntity.accepted().build(); // Returns 202 Accepted
	}

}
