package com.cts.controller;

import com.cts.dto.BikeDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.security.JwtTokenProvider;
import com.cts.service.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BikeService bikeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    private BikeDto bike1;
    private BikeDto bike2;
    private Bike bikeEntity;

    @BeforeEach
    void setUp() {
        // Generate token using JwtTokenProvider
        var auth = new UsernamePasswordAuthenticationToken("srajan", null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        jwtToken = "Bearer " + jwtTokenProvider.generateToken(auth);

        bike1 = new BikeDto(1, "Honda", "CB350", "KA19MA1234", "12345678901234567",
                "Brake pad issue", 145000, null, null, null, null, null);

        bike2 = new BikeDto(2, "Yamaha", "R15", "TN10AB5678", "98765432101234567",
                "Battery issue", 175000, null, null, null, null, null);

        bikeEntity = new Bike(1L, "Honda", "CB350", "KA19MA1234", "12345678901234567",
                "Brake pad issue", 145000,
                LocalDateTime.of(2025, 5, 15, 10, 30),
                LocalDate.of(2025, 5, 25),
                LocalDateTime.of(2025, 5, 15, 10, 30),
                LocalDateTime.now(),
                new Customer(1L, "Rahul Sharma", "9123456780", "22C", "Green Avenue",
                        "Near Metro Station", "Mumbai", "Maharashtra", "400001")
        );
    }

    @Test
    @DisplayName("Get All Bikes")
    void testGetAllBikes() throws Exception {
        when(bikeService.getAll()).thenReturn(List.of(bike1, bike2));

        mockMvc.perform(get("/api/bikes")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].bikeMake").value("Honda"));
    }

    @Test
    @DisplayName("Get Bike By ID")
    void testGetBikeById() throws Exception {
        when(bikeService.getById(1L)).thenReturn(bike1);

        mockMvc.perform(get("/api/bikes/1")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bikeMake").value("Honda"));
    }

    @Test
    @DisplayName("Save Bike")
    void testSaveBike() throws Exception {
        when(bikeService.addBike(any(BikeDto.class))).thenReturn(bikeEntity);

        var json = objectMapper.writeValueAsString(bike1);

        mockMvc.perform(post("/api/bikes/save")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update Bike")
    void testUpdateBike() throws Exception {
        when(bikeService.updateBike(eq(1L), any(BikeDto.class))).thenReturn(bikeEntity);

        var json = objectMapper.writeValueAsString(bike1);

        mockMvc.perform(put("/api/bikes/1")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bikeMake").value("Honda"));
    }

    @Test
    @DisplayName("Delete Bike")
    void testDeleteBike() throws Exception {
        doNothing().when(bikeService).deleteBike(1L);

        mockMvc.perform(delete("/api/bikes/1")
                        .header("Authorization", jwtToken))
                .andExpect(status().isAccepted());
    }
}
