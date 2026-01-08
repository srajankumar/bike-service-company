package com.cts;

import com.cts.dto.BikeDto;
import com.cts.dto.CustomerDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class BikeserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BikeTestRepository bikeTestRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		bikeTestRepository.deleteAll();

		// Setup initial data in H2
		Customer customer = new Customer();
		customer.setCustomerName("Integration User");
		customer.setPhoneNumber("9999999999");

		Bike bike = new Bike();
		bike.setBikeMake("Suzuki");
		bike.setModelName("Gixxer");
		bike.setBikeRegistrationNumber("KA01HH1234");
		bike.setCustomer(customer);

		bikeTestRepository.save(bike);
	}

	// Integration Test: Get all bikes from H2
	@Test
	@DisplayName("Get all bikes")
	@WithMockUser
	void testGetAllBikes() throws Exception {
		mockMvc.perform(get("/api/bikes")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(bikeTestRepository.findAll().size())));
	}

	// Integration Test: Get bike by ID from H2 - Success
	@Test
	@DisplayName("Get bike by ID - Success")
	@WithMockUser
	void testGetBikeByIdSuccess() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerName("Test User");

		Bike bike = new Bike();
		bike.setBikeMake("Honda");
		bike.setModelName("CB350");
		bike.setBikeRegistrationNumber("KA19MA1234");
		bike.setCustomer(customer);

		// Save and capture the generated ID
		Bike savedBike = bikeTestRepository.save(bike);
		Long id = savedBike.getBikeId();

		mockMvc.perform(get("/api/bikes/{id}", id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.bikeMake").value("Honda")).andExpect(jsonPath("$.modelName").value("CB350"))
				.andExpect(jsonPath("$.bikeRegistrationNumber").value("KA19MA1234"));
	}

	// Integration Test: Get bike by ID from H2 - Not Found
	@Test
	@DisplayName("Get bike by ID - Not Found")
	@WithMockUser
	void testGetBikeByIdNotFound() throws Exception {
		mockMvc.perform(get("/api/bikes/999")).andExpect(status().isNotFound());
	}

	// Integration Test: Delete bike from H2
	@Test
	@DisplayName("Delete bike from H2")
	@WithMockUser(roles = "ADMIN")
	void testDeleteBike() throws Exception {
		Long bikeId = bikeTestRepository.findAll().get(0).getBikeId();
		mockMvc.perform(delete("/api/bikes/" + bikeId)).andExpect(status().isAccepted());

		// Verify DB is empty
		mockMvc.perform(get("/api/bikes")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
	}

	// Integration Test: Add bike to H2
	@Test
	@DisplayName("Add bike")
	@WithMockUser(roles = "ADMIN")
	void testAddBike() throws Exception {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCity("Mumbai");
		customerDto.setCustomerName("Rahul Sharma");
		customerDto.setHouseNo("22C");
		customerDto.setLandmark("Near Metro Station");
		customerDto.setPhoneNumber("9123456780");
		customerDto.setPin("400001");
		customerDto.setState("Maharashtra");
		customerDto.setStreet("Green Avenue");

		BikeDto bikeDto = new BikeDto();
		bikeDto.setBikeChassisNumber("12345678901234567");
		bikeDto.setBikeMake("Honda");
		bikeDto.setBikeRegistrationNumber("KA19MA1234");
		bikeDto.setCost(145000);
		bikeDto.setCreatedDateAndTime(LocalDateTime.of(2025, 5, 15, 10, 30));
		bikeDto.setExpectedDeliveryDate(LocalDate.of(2025, 5, 25));
		bikeDto.setGivenDate(LocalDateTime.of(2025, 5, 15, 10, 30));
		bikeDto.setKnownIssues("Brake pad issue");
		bikeDto.setModelName("CB350");
		bikeDto.setUpdatedDateAndTime(LocalDateTime.now());
		bikeDto.setCustomer(customerDto);

		String jsonContent = objectMapper.writeValueAsString(bikeDto);

		mockMvc.perform(post("/api/bikes/save").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.bikeMake").value("Honda"));
	}

	// Integration Test: Update bike from H2
	@Test
	@DisplayName("Update bike")
	@WithMockUser(roles = "ADMIN")
	void testUpdateBike() throws Exception {
		// Add bike
		Customer customer = new Customer();
		customer.setCity("Mumbai");
		customer.setCustomerName("Rahul Sharma");
		customer.setHouseNo("22C");
		customer.setLandmark("Near Metro Station");
		customer.setPhoneNumber("9123456780");
		customer.setPin("400001");
		customer.setState("Maharashtra");
		customer.setStreet("Green Avenue");

		Bike bike = new Bike();
		bike.setBikeChassisNumber("12345678901234567");
		bike.setBikeMake("Honda");
		bike.setBikeRegistrationNumber("KA19MA1234");
		bike.setCost(145000);
		bike.setCreatedDateAndTime(LocalDateTime.of(2025, 5, 15, 10, 30));
		bike.setExpectedDeliveryDate(LocalDate.of(2025, 5, 25));
		bike.setGivenDate(LocalDateTime.of(2025, 5, 15, 10, 30));
		bike.setKnownIssues("Brake pad issue");
		bike.setModelName("CB350");
		bike.setUpdatedDateAndTime(LocalDateTime.now());
		bike.setCustomer(customer);

		Bike savedBike = bikeTestRepository.save(bike);
		Long bikeId = savedBike.getBikeId();

		// Prepare update data
		BikeDto updateRequest = new BikeDto();
		updateRequest.setBikeMake("Honda Updated");
		updateRequest.setModelName("CB350 RS");
		updateRequest.setCost(150000);

		CustomerDto custDto = new CustomerDto();
		custDto.setCustomerName("Original Owner");
		updateRequest.setCustomer(custDto);

		mockMvc.perform(put("/api/bikes/{id}", bikeId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.bikeMake").value("Honda Updated"))
				.andExpect(jsonPath("$.modelName").value("CB350 RS"));
	}

}