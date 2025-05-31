package com.cts.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cts.dto.BikeDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.dto.CustomerDto;
import com.cts.exceptions.BikeNotFoundException;
import com.cts.repository.BikeRepository;

// Implements bike-related operations such as retrieving, adding, updating, and deleting bikes
@Service
public class BikeServiceImpl implements BikeService {

	private BikeRepository bikeRepository;

	// Constructor injection
	public BikeServiceImpl(BikeRepository bikeRepository) {
		super();
		this.bikeRepository = bikeRepository;
	}

	// Get all bikes
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

			CustomerDto customerDto = new CustomerDto();
			customerDto.setCustomerId(bike.getCustomer().getCustomerId());
			customerDto.setCustomerName(bike.getCustomer().getCustomerName());
			customerDto.setPhoneNumber(bike.getCustomer().getPhoneNumber());
			customerDto.setHouseNo(bike.getCustomer().getHouseNo());
			customerDto.setStreet(bike.getCustomer().getStreet());
			customerDto.setLandmark(bike.getCustomer().getLandmark());
			customerDto.setCity(bike.getCustomer().getCity());
			customerDto.setState(bike.getCustomer().getState());
			customerDto.setPin(bike.getCustomer().getPin());

			bikeDto.setCustomer(customerDto);

			bikeDtos.add(bikeDto);
		}
		return bikeDtos;
	}

	// Get bike by ID
	@Override
	public BikeDto getById(long id) {
		Bike bike = bikeRepository.findById(id)
				.orElseThrow(() -> new BikeNotFoundException("Bike with ID: " + id + " not found"));

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

		if (bike.getCustomer() != null) {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setCustomerId(bike.getCustomer().getCustomerId());
			customerDto.setCustomerName(bike.getCustomer().getCustomerName());
			customerDto.setPhoneNumber(bike.getCustomer().getPhoneNumber());
			customerDto.setHouseNo(bike.getCustomer().getHouseNo());
			customerDto.setStreet(bike.getCustomer().getStreet());
			customerDto.setLandmark(bike.getCustomer().getLandmark());
			customerDto.setCity(bike.getCustomer().getCity());
			customerDto.setState(bike.getCustomer().getState());
			customerDto.setPin(bike.getCustomer().getPin());
			
			bikeDto.setCustomer(customerDto);
		}

		return bikeDto;
	}

	// Add a new bike
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
		
		bike.setCreatedDateAndTime(LocalDateTime.now());
		bike.setUpdatedDateAndTime(LocalDateTime.now());

		Customer customer = new Customer();
		customer.setCustomerName(bikeDto.getCustomer().getCustomerName());
		customer.setPhoneNumber(bikeDto.getCustomer().getPhoneNumber());
		customer.setHouseNo(bikeDto.getCustomer().getHouseNo());
		customer.setStreet(bikeDto.getCustomer().getStreet());
		customer.setLandmark(bikeDto.getCustomer().getLandmark());
		customer.setCity(bikeDto.getCustomer().getCity());
		customer.setState(bikeDto.getCustomer().getState());
		customer.setPin(bikeDto.getCustomer().getPin());

		bike.setCustomer(customer);

		return bikeRepository.save(bike);
	}

	// Update bike details
	@Override
	public Bike updateBike(long id, BikeDto bikeDto) {
		if (!bikeRepository.existsById(id)) {
			throw new BikeNotFoundException("Bike with id: " + id + " not found");
		}

		Bike bike = bikeRepository.findById(id).get();

		if (bikeDto.getBikeMake() != null) 
			bike.setBikeMake(bikeDto.getBikeMake());
		if (bikeDto.getModelName() != null)
			bike.setModelName(bikeDto.getModelName());
		if (bikeDto.getBikeRegistrationNumber() != null)
			bike.setBikeRegistrationNumber(bikeDto.getBikeRegistrationNumber());
		if (bikeDto.getBikeChassisNumber() != null)
			bike.setBikeChassisNumber(bikeDto.getBikeChassisNumber());
		if (bikeDto.getKnownIssues() != null)
			bike.setKnownIssues(bikeDto.getKnownIssues());
		if (bikeDto.getCost() != 0)
			bike.setCost(bikeDto.getCost());
		if(bikeDto.getGivenDate() != null)
			bike.setGivenDate(bikeDto.getGivenDate());
		if (bikeDto.getExpectedDeliveryDate() != null)
			bike.setExpectedDeliveryDate(bikeDto.getExpectedDeliveryDate());

		bike.setUpdatedDateAndTime(LocalDateTime.now());

		if (bike.getCustomer() != null) {
			Customer customer = bike.getCustomer();

			if (bikeDto.getCustomer().getCustomerName() != null)
				customer.setCustomerName(bikeDto.getCustomer().getCustomerName());
			if (bikeDto.getCustomer().getPhoneNumber() != null)
				customer.setPhoneNumber(bikeDto.getCustomer().getPhoneNumber());
			if (bikeDto.getCustomer().getHouseNo() != null)
				customer.setHouseNo(bikeDto.getCustomer().getHouseNo());
			if (bikeDto.getCustomer().getStreet() != null)
				customer.setStreet(bikeDto.getCustomer().getStreet());
			if (bikeDto.getCustomer().getLandmark() != null)
				customer.setLandmark(bikeDto.getCustomer().getLandmark());
			if (bikeDto.getCustomer().getCity() != null)
				customer.setCity(bikeDto.getCustomer().getCity());
			if (bikeDto.getCustomer().getState() != null)
				customer.setState(bikeDto.getCustomer().getState());
			if (bikeDto.getCustomer().getPin() != null)
				customer.setPin(bikeDto.getCustomer().getPin());

			bike.setCustomer(customer);
		}

		return bikeRepository.save(bike);
	}

	// Delete bike details
	@Override
	public void deleteBike(long id) {
		if (!bikeRepository.existsById(id)) {
			throw new BikeNotFoundException("Bike with ID: " + id + " not found");
		}
		bikeRepository.deleteById(id);
	}

}
