package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.dto.BikeDto;
import com.cts.dto.CustomerDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.repository.BikeRepository;

@ExtendWith(MockitoExtension.class)
class BikeServiceImplTest {

	@Mock
	private BikeRepository bikeRepository;

	@InjectMocks
	private BikeServiceImpl bikeService;

	private Bike bike1;
	private Bike bike2;
	private Customer customer;
	private List<Bike> bikes;
	private CustomerDto customerDto;
	private BikeDto bikeDto;

	@BeforeEach
	void init() {
		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("Rahul Sharma");
		customer.setPhoneNumber("9876543210");
		customer.setCity("Udupi");

		customerDto = new CustomerDto();
		customerDto.setCustomerName("Rahul Sharma");
		customerDto.setPhoneNumber("9876543210");
		customerDto.setCity("Udupi");

		bike1 = new Bike(1, "Honda", "CB350", "KA19MA1234", "12345678901234567", "Brake pad issue", 145000,
				LocalDateTime.of(2025, 5, 15, 10, 30), LocalDate.of(2025, 5, 25), LocalDateTime.of(2025, 5, 15, 10, 30),
				LocalDateTime.now(), customer);

		bike2 = new Bike(2, "Yamaha", "R15", "TN10AB5678", "98765432101234567", "Battery issue", 175000,
				LocalDateTime.of(2025, 6, 10, 11, 00), LocalDate.of(2025, 6, 18), LocalDateTime.of(2025, 6, 10, 11, 00),
				LocalDateTime.now(), customer);

		bikeDto = new BikeDto();
		bikeDto.setBikeMake("Honda");
		bikeDto.setModelName("CB350");
		bikeDto.setBikeRegistrationNumber("KA19MA1234");
		bikeDto.setCustomer(customerDto);

		bikes = new ArrayList<>();
		bikes.add(bike1);
		bikes.add(bike2);
	}

	// Test: Get all bikes
	@Test
	@DisplayName("Get all bikes")
	void testGetAllBikes() {
		when(bikeRepository.findAll()).thenReturn(bikes);

		var result = bikeService.getAll();

		assertEquals(2, result.size());
		assertEquals("Honda", result.get(0).getBikeMake());
		verify(bikeRepository, times(1)).findAll();
	}

	// Test: Get bike by ID
	@Test
	@DisplayName("Get bike By ID")
	void testGetBikeById() {
		when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike1));

		var bikeDto = bikeService.getById(1L);

		assertNotNull(bikeDto);
		assertEquals("Honda", bikeDto.getBikeMake());
		verify(bikeRepository, times(1)).findById(1L);
	}

	// Test: Save bike
	@Test
	@DisplayName("Save Bike")
	void testSaveBike() {
		when(bikeRepository.save(any(Bike.class))).thenReturn(bike1);
		var result = bikeService.addBike(bikeDto);
		assertNotNull(result);
		assertEquals(bike1, result);
		assertEquals("Honda", result.getBikeMake());
		assertEquals(customer, result.getCustomer());
		verify(bikeRepository, times(1)).save(any(Bike.class));
	}

// Test: Update bike
	@Test
	@DisplayName("Update bike")
	void testUpdateBike() {
		when(bikeRepository.existsById(anyLong())).thenReturn(true);
		when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike1));
		when(bikeRepository.save(any(Bike.class))).thenReturn(bike2);
		var result = bikeService.updateBike(2, bikeDto);
		assertEquals(bike2, result);
		assertEquals("Yamaha", result.getBikeMake());
		verify(bikeRepository, times(1)).existsById(anyLong());
		verify(bikeRepository, times(1)).findById(anyLong());
		verify(bikeRepository, times(1)).save(any(Bike.class));
	}

	// Test: Delete bike
	@Test
	@DisplayName("Delete bike")
	void testDeleteBike() {
		when(bikeRepository.existsById(anyLong())).thenReturn(true);
		doNothing().when(bikeRepository).deleteById(anyLong());

		bikeService.deleteBike(1L);

		verify(bikeRepository, times(1)).deleteById(1L);
	}
}