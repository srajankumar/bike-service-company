package com.cts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cts.dto.BikeDto;
import com.cts.service.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BikeService bikeService;

    @Autowired
    private ObjectMapper mapper;

    private BikeDto bike1;
    private BikeDto bike2;
    private List<BikeDto> bikeList;

    @BeforeEach
    void init() {
        bike1 = new BikeDto(1, "Honda", "CB350", "KA19MA1234", "12345678901234567", "Brake pad issue", 145000, null, null, null, null, null);
        bike2 = new BikeDto(2, "Yamaha", "R15", "TN10AB5678", "98765432101234567", "Battery issue", 175000, null, null, null, null, null);

        bikeList = new ArrayList<>();
        bikeList.add(bike1);
        bikeList.add(bike2);
        
        System.out.println("SIZE========================================" + bikeList.size());
    }

    @Test
    void testGetAllBikes() throws Exception {
        when(bikeService.getAll()).thenReturn(List.of(bike1,bike2));
        
        System.out.println(bikeList.size()+"=============================");
        
        var result=bikeService.getAll();
        System.out.println(result);
        

        mockMvc.perform(get("/api/bikes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].bikeMake").value("Honda"));
    }

    @Test
//    @Disabled
    void testGetBikeById() throws Exception {
        when(bikeService.getById(anyLong())).thenReturn(bike1);

        mockMvc.perform(get("/api/bikes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bikeMake").value("Honda"));
    }

//    @Test
//    void testSaveBike() throws Exception {
//        when(bikeService.addBike(any(BikeDto.class))).thenReturn(bike1);
//        var jsonBike = mapper.writeValueAsString(bike1);
//
//        mockMvc.perform(post("/api/bikes/save")
//            .content(jsonBike)
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isCreated());
//    }

//    @Test
//    void testUpdateBike() throws Exception {
//        when(bikeService.updateBike(anyLong(), any(BikeDto.class))).thenReturn(bike1);
//        var jsonBike = mapper.writeValueAsString(bike1);
//
//        mockMvc.perform(put("/api/bikes/1")
//            .content(jsonBike)
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk());
//    }

    @Test
    @Disabled
    void testDeleteBike() throws Exception {
        doNothing().when(bikeService).deleteBike(anyLong());

        mockMvc.perform(delete("/api/bikes/1"))
            .andExpect(status().isNoContent());
    }
}
