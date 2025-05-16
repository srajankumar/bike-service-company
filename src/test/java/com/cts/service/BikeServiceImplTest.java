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
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dto.BikeDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.repository.BikeRepository;
import com.cts.repository.CustomerRepository;

@SpringBootTest
class BikeServiceImplTest {

    @Mock
    private BikeRepository bikeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BikeServiceImpl bikeService;

    private Bike bike1;
    private Bike bike2;
    private Customer customer1;
    private List<Bike> bikes;

    @BeforeEach
    void init() {
        customer1 = new Customer();
        customer1.setCustomerName("Rahul Sharma");

        bike1 = new Bike(1, "Honda", "CB350", "KA19MA1234", "12345678901234567", "Brake pad issue", 145000, LocalDateTime.of(2025, 5, 15, 10, 30), LocalDate.of(2025, 5, 25), LocalDateTime.of(2025, 5, 15, 10, 30), LocalDateTime.now(), customer1);
        bike2 = new Bike(2, "Yamaha", "R15", "TN10AB5678", "98765432101234567", "Battery issue", 175000, LocalDateTime.of(2025, 6, 10, 11, 00), LocalDate.of(2025, 6, 18), LocalDateTime.of(2025, 6, 10, 11, 00), LocalDateTime.now(), customer1);

        bikes = new ArrayList<>();
        bikes.add(bike1);
        bikes.add(bike2);
    }

    @Test
    @DisplayName("Get All Bikes")
    void testGetAllBikes() {
        when(bikeRepository.findAll()).thenReturn(bikes);

        var result = bikeService.getAll();

        assertEquals(2, result.size());
        assertEquals("Honda", result.get(0).getBikeMake());

        verify(bikeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get Bike By ID")
    void testGetBikeById() {
        when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike1));

        var bikeDto = bikeService.getById(1L);

        assertNotNull(bikeDto);
        assertEquals("Honda", bikeDto.getBikeMake());

        verify(bikeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Save Bike")
    void testSaveBike() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        when(bikeRepository.save(any(Bike.class))).thenReturn(bike1);

        BikeDto bikeDto = new BikeDto();
        bikeDto.setBikeMake("Honda");
        bikeDto.setModelName("CB350");
        bikeDto.setBikeRegistrationNumber("KA19MA1234");
        bikeDto.setCustomer(customer1);

        var savedBike = bikeService.addBike(bikeDto);

        assertNotNull(savedBike);
        assertEquals("Honda", savedBike.getBikeMake());

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(bikeRepository, times(1)).save(any(Bike.class));
    }

    @Test
    @DisplayName("Update Bike")
    void testUpdateBike() {
        when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike1));
        when(bikeRepository.save(any(Bike.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BikeDto updatedBikeDto = new BikeDto();
        updatedBikeDto.setBikeMake("Royal Enfield");

        var result = bikeService.updateBike(1, updatedBikeDto);

        assertNotNull(result);
        assertEquals("Royal Enfield", result.getBikeMake());

        verify(bikeRepository, times(1)).findById(1L);
        verify(bikeRepository, times(1)).save(any(Bike.class));
    }


    @Test
    @DisplayName("Delete Bike")
    void testDeleteBike() {
        when(bikeRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(bikeRepository).deleteById(anyLong());

        bikeService.deleteBike(1L);

        verify(bikeRepository, times(1)).deleteById(1L);
    }

}
