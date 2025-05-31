package com.cts.service;

import java.util.List;

import com.cts.dto.BikeDto;
import com.cts.entities.Bike;

// Defines the operations related to bike management
public interface BikeService {
	
	List<BikeDto> getAll();
	BikeDto getById(long id);
	Bike addBike(BikeDto bikeDto);
    Bike updateBike(long id, BikeDto bikeDto);
    void deleteBike(long id);
}
