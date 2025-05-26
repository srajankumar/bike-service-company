package com.cts.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.cts.security.CustomUserDetailsService;
import org.springframework.stereotype.Service;
import com.cts.BikeserviceApplication;
import com.cts.dto.BikeDto;
import com.cts.dto.BikeUpdateDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.dto.CustomerDto;
import com.cts.exceptions.BikeNotFoundException;
import com.cts.repository.BikeRepository;
import com.cts.repository.CustomerRepository;

// Implements bike-related operations such as retrieving, adding, updating, and deleting bikes
@Service
public class BikeServiceImpl implements BikeService {

	private final CustomUserDetailsService customUserDetailsService;
	private final BikeserviceApplication bikeserviceApplication;

	private BikeRepository bikeRepository;
	private CustomerRepository customerRepository;

	// Constructor injection
	public BikeServiceImpl(BikeRepository bikeRepository, CustomerRepository customerRepository,
			BikeserviceApplication bikeserviceApplication, CustomUserDetailsService customUserDetailsService) {
		super();
		this.bikeRepository = bikeRepository;
		this.customerRepository = customerRepository;
		this.bikeserviceApplication = bikeserviceApplication;
		this.customUserDetailsService = customUserDetailsService;
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
		bike.setCreatedDateAndTime(bikeDto.getCreatedDateAndTime());
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
	public Bike updateBike(long id, BikeUpdateDto bikeUpdateDto) {
		if (!bikeRepository.existsById(id)) {
			throw new BikeNotFoundException("Bike with id: " + id + " not found");
		}

		Bike bike = bikeRepository.findById(id).get();

		if (bikeUpdateDto.getBikeMake() != null) 
			bike.setBikeMake(bikeUpdateDto.getBikeMake());
		if (bikeUpdateDto.getModelName() != null)
			bike.setModelName(bikeUpdateDto.getModelName());
		if (bikeUpdateDto.getBikeRegistrationNumber() != null)
			bike.setBikeRegistrationNumber(bikeUpdateDto.getBikeRegistrationNumber());
		if (bikeUpdateDto.getBikeChassisNumber() != null)
			bike.setBikeChassisNumber(bikeUpdateDto.getBikeChassisNumber());
		if (bikeUpdateDto.getKnownIssues() != null)
			bike.setKnownIssues(bikeUpdateDto.getKnownIssues());
		if (bikeUpdateDto.getCost() != 0)
			bike.setCost(bikeUpdateDto.getCost());
		if (bikeUpdateDto.getExpectedDeliveryDate() != null)
			bike.setExpectedDeliveryDate(bikeUpdateDto.getExpectedDeliveryDate());

		bike.setUpdatedDateAndTime(LocalDateTime.now());

		if (bike.getCustomer() != null) {
			Customer customer = bike.getCustomer();

			if (bikeUpdateDto.getCustomer().getCustomerName() != null)
				customer.setCustomerName(bikeUpdateDto.getCustomer().getCustomerName());
			if (bikeUpdateDto.getCustomer().getPhoneNumber() != null)
				customer.setPhoneNumber(bikeUpdateDto.getCustomer().getPhoneNumber());
			if (bikeUpdateDto.getCustomer().getHouseNo() != null)
				customer.setHouseNo(bikeUpdateDto.getCustomer().getHouseNo());
			if (bikeUpdateDto.getCustomer().getStreet() != null)
				customer.setStreet(bikeUpdateDto.getCustomer().getStreet());
			if (bikeUpdateDto.getCustomer().getLandmark() != null)
				customer.setLandmark(bikeUpdateDto.getCustomer().getLandmark());
			if (bikeUpdateDto.getCustomer().getCity() != null)
				customer.setCity(bikeUpdateDto.getCustomer().getCity());
			if (bikeUpdateDto.getCustomer().getState() != null)
				customer.setState(bikeUpdateDto.getCustomer().getState());
			if (bikeUpdateDto.getCustomer().getPin() != null)
				customer.setPin(bikeUpdateDto.getCustomer().getPin());

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
