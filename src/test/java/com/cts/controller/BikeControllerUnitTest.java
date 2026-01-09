package com.cts.controller;

import com.cts.dto.BikeDto;
import com.cts.dto.CustomerDto;
import com.cts.entities.Bike;
import com.cts.service.BikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BikeControllerUnitTest {

	@Mock
	private BikeService bikeService;

	@InjectMocks
	private BikeController bikeController;

	private BikeDto bike1;

	@BeforeEach
	void setUp() {

		bike1 = new BikeDto(1, "Honda", "CB350", "KA19MA1234", "12345678901234567", "Brake pad issue", 145000,
				LocalDateTime.of(2025, 5, 15, 10, 30), LocalDate.of(2025, 5, 25), LocalDateTime.of(2025, 5, 15, 10, 30),
				LocalDateTime.now(), new CustomerDto(1L, "Rahul Sharma", "9123456780", "22C", "Green Avenue",
						"Near Metro Station", "Mumbai", "Maharashtra", "400001"));
	}

	@Test
	@DisplayName("Get all bikes")
	void testGetAllBikes() {

		when(bikeService.getAll()).thenReturn(List.of(bike1));

		List<BikeDto> result = bikeController.getAll();

		assertEquals(1, result.size());
		assertEquals("Honda", result.get(0).getBikeMake());
	}

	@Test
	@DisplayName("Get bike By ID")
	void testGetBikeById() {

		when(bikeService.getById(1L)).thenReturn(bike1);

		BikeDto result = bikeController.getById(1L);

		assertEquals("Honda", result.getBikeMake());
		assertEquals("CB350", result.getModelName());
	}

	@Test
	@DisplayName("Save bike")
	void testSaveBike() {

		Bike bikeEntity = new Bike();
		bikeEntity.setBikeMake("Honda");

		when(bikeService.addBike(any(BikeDto.class))).thenReturn(bikeEntity);

		ResponseEntity<Bike> result = bikeController.addBike(bike1);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals("Honda", result.getBody().getBikeMake());
	}

	@Test
	@DisplayName("Update bike")
	void testUpdateBike() {

		Bike updatedEntity = new Bike();
		updatedEntity.setBikeMake("Honda-Updated");

		when(bikeService.updateBike(eq(1L), any(BikeDto.class))).thenReturn(updatedEntity);

		ResponseEntity<Bike> result = bikeController.updateBike(1L, bike1);

		assertEquals("Honda-Updated", result.getBody().getBikeMake());
	}

	@Test
	@DisplayName("Delete bike")
	void testDeleteBike() {

		bikeController.deleteBike(1L);

		verify(bikeService, times(1)).deleteBike(1L);
	}
}
