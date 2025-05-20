package com.cts.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.cts.BikeserviceApplication;
import com.cts.dto.BikeDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.dto.CustomerDto;
import com.cts.exceptions.BikeNotFoundException;
import com.cts.repository.BikeRepository;
import com.cts.repository.CustomerRepository;

@Service
public class BikeServiceImpl implements BikeService {

	private final BikeserviceApplication bikeserviceApplication;

	private BikeRepository bikeRepository;
	private CustomerRepository customerRepository;

	// Constructor injection
	public BikeServiceImpl(BikeRepository bikeRepository, CustomerRepository customerRepository,
			BikeserviceApplication bikeserviceApplication) {
		super();
		this.bikeRepository = bikeRepository;
		this.customerRepository = customerRepository;
		this.bikeserviceApplication = bikeserviceApplication;
	}

	@Override
	public List<BikeDto> getAll() {
		List<Bike> bikes = bikeRepository.findAll();
		List<BikeDto> bikeDtos = new ArrayList<>();
		for (Bike bike : bikes) {
			BikeDto bikeDto = new BikeDto();
			bikeDto.setBikeId(bike.getBikeId());
			bikeDto.setBikeMake(bike.getBikeMake());
			bikeDto.setModelName(bike.getModelName());
			bikeDto.setBikeRegistrationNumber(bike.getBikeRegistrationNumber());
			bikeDto.setBikeChassisNumber(bike.getBikeChassisNumber());
			bikeDto.setKnownIssues(bike.getKnownIssues());
			bikeDto.setCost(bike.getCost());
			bikeDto.setGivenDate(bike.getGivenDate());
			bikeDto.setExpectedDeliveryDate(bike.getExpectedDeliveryDate());
			bikeDto.setCreatedDateAndTime(bike.getCreatedDateAndTime());
			bikeDto.setUpdatedDateAndTime(bike.getUpdatedDateAndTime());
			bikeDto.setCustomer(bike.getCustomer());

			bikeDtos.add(bikeDto);
		}
		return bikeDtos;
	}

	@Override
	public BikeDto getById(long id) {
		Bike bike = bikeRepository.findById(id).orElseThrow(() -> new BikeNotFoundException("Bike with ID: " + id + " not found"));
		if(bike != null) {
			BikeDto bikeDto= new BikeDto(bike.getBikeId(), bike.getBikeMake(), bike.getModelName(), bike.getBikeRegistrationNumber(), bike.getBikeChassisNumber(), bike.getKnownIssues(), bike.getCost(), bike.getGivenDate(), bike.getExpectedDeliveryDate(), bike.getCreatedDateAndTime(), bike.getUpdatedDateAndTime(), bike.getCustomer());
			return bikeDto;
		}
		return null;
	}

	@Override
	public Bike addBike(BikeDto bikeDto) {
	    Bike bike = new Bike();
	    
	    bike.setBikeMake(bikeDto.getBikeMake());
	    bike.setModelName(bikeDto.getModelName());
	    bike.setBikeRegistrationNumber(bikeDto.getBikeRegistrationNumber());
	    bike.setBikeChassisNumber(bikeDto.getBikeChassisNumber());
	    bike.setKnownIssues(bikeDto.getKnownIssues());
	    bike.setCost(bikeDto.getCost());
	    bike.setGivenDate(bikeDto.getGivenDate());
	    bike.setExpectedDeliveryDate(bikeDto.getExpectedDeliveryDate());
	    bike.setCreatedDateAndTime(bikeDto.getCreatedDateAndTime());
	    bike.setUpdatedDateAndTime(LocalDateTime.now());

	    Customer savedCustomer = customerRepository.save(bikeDto.getCustomer());
	    bike.setCustomer(savedCustomer);

	    return bikeRepository.save(bike);
	}

	@Override
	public Bike updateBike(long id, BikeDto bikeDto) {
	    Bike existingBike = bikeRepository.findById(id)
	            .orElseThrow(() -> new BikeNotFoundException("Bike with ID: " + id + " not found"));

	    if (bikeDto.getBikeMake() != null) {
	        existingBike.setBikeMake(bikeDto.getBikeMake());
	    }
	    if (bikeDto.getModelName() != null) {
	        existingBike.setModelName(bikeDto.getModelName());
	    }
	    if (bikeDto.getBikeRegistrationNumber() != null) {
	        existingBike.setBikeRegistrationNumber(bikeDto.getBikeRegistrationNumber());
	    }
	    if (bikeDto.getBikeChassisNumber() != null) {
	        existingBike.setBikeChassisNumber(bikeDto.getBikeChassisNumber());
	    }
	    if (bikeDto.getKnownIssues() != null) {
	        existingBike.setKnownIssues(bikeDto.getKnownIssues());
	    }

	    // Ensures cost is positive
	    if (bikeDto.getCost() > 0) {
	        existingBike.setCost(bikeDto.getCost());
	    }
	    if (bikeDto.getGivenDate() != null) {
	        existingBike.setGivenDate(bikeDto.getGivenDate());
	    }
	    if (bikeDto.getExpectedDeliveryDate() != null) {
	        existingBike.setExpectedDeliveryDate(bikeDto.getExpectedDeliveryDate());
	    }

	    // Auto update timestamp
	    existingBike.setUpdatedDateAndTime(LocalDateTime.now());

	    if (bikeDto.getCustomer() != null) {
	        Customer existingCustomer = existingBike.getCustomer(); // Get current customer

	        if (existingCustomer == null) {
	            existingCustomer = new Customer(); // Create new customer if null
	        }

	        Customer customerDto = bikeDto.getCustomer();

	        if (customerDto.getCustomerName() != null) {
	            existingCustomer.setCustomerName(customerDto.getCustomerName());
	        }
	        if (customerDto.getPhoneNumber() != null) {
	            existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
	        }
	        if (customerDto.getHouseNo() != null) {
	            existingCustomer.setHouseNo(customerDto.getHouseNo());
	        }
	        if (customerDto.getStreet() != null) {
	            existingCustomer.setStreet(customerDto.getStreet());
	        }
	        if (customerDto.getLandmark() != null) {
	            existingCustomer.setLandmark(customerDto.getLandmark());
	        }
	        if (customerDto.getCity() != null) {
	            existingCustomer.setCity(customerDto.getCity());
	        }
	        if (customerDto.getState() != null) {
	            existingCustomer.setState(customerDto.getState());
	        }
	        if (customerDto.getPin() != null) {
	            existingCustomer.setPin(customerDto.getPin());
	        }

	        Customer savedCustomer = customerRepository.save(existingCustomer);
	        existingBike.setCustomer(savedCustomer);
	    }

	    return bikeRepository.save(existingBike);
	}



	@Override
	public void deleteBike(long id) {
	    if (!bikeRepository.existsById(id)) {
	        throw new BikeNotFoundException("Bike with ID: " + id + " not found");
	    }
	    bikeRepository.deleteById(id);
	}


}
