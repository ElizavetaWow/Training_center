package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Place;
import com.krylova.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlaceControllerTest {

    @MockBean
    private PlaceService placeService;

    @Autowired
    private MockMvc mockMvc;

    Place place = new Place();
    Place place1 = new Place();

    @BeforeEach
    void setUp() {
        place.setId(1L);
        place.setCity("Moscow");
        place.setStreet("Vavilova");
        place.setBuilding("100");
        place.setRoom(14);

        place1.setId(2L);
        place1.setCity("Moscow");
        place1.setStreet("Vavilova");
        place1.setBuilding("58K");
        place1.setRoom(2);
    }

    @Test
    @DisplayName("Create a place")
    void create() throws Exception {
        when(placeService.create(any())).thenReturn(place);
        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(place)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all places")
    void findAll() throws Exception {
        when(placeService.findAll()).thenReturn(List.of(place, place1));
        mockMvc.perform(get("/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].city", is("Moscow")))
                .andExpect(jsonPath("$[0].street", is("Vavilova")))
                .andExpect(jsonPath("$[0].building", is("100")))
                .andExpect(jsonPath("$[0].room", is(14)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].city", is("Moscow")))
                .andExpect(jsonPath("$[1].street", is("Vavilova")))
                .andExpect(jsonPath("$[1].building", is("58K")))
                .andExpect(jsonPath("$[1].room", is(2)));
    }

    @Test
    @DisplayName("Find a place by ID")
    void find() throws Exception {
        when(placeService.findById(1L)).thenReturn(Optional.of(place));
        mockMvc.perform(get("/places/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.city", is("Moscow")))
                .andExpect(jsonPath("$.street", is("Vavilova")))
                .andExpect(jsonPath("$.building", is("100")))
                .andExpect(jsonPath("$.room", is(14)));
    }

    @Test
    @DisplayName("Find places by city")
    void findByCity() throws Exception {
        when(placeService.findByCity("Moscow")).thenReturn(List.of(place, place1));
        mockMvc.perform(get("/places/city_Moscow"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].city", is("Moscow")))
                .andExpect(jsonPath("$[0].street", is("Vavilova")))
                .andExpect(jsonPath("$[0].building", is("100")))
                .andExpect(jsonPath("$[0].room", is(14)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].city", is("Moscow")))
                .andExpect(jsonPath("$[1].street", is("Vavilova")))
                .andExpect(jsonPath("$[1].building", is("58K")))
                .andExpect(jsonPath("$[1].room", is(2)));
    }

    @Test
    @DisplayName("Update a place")
    void updatePlace() throws Exception {
        Place place2 = place;
        place2.setRoom(188);
        when(placeService.findById(1L)).thenReturn(Optional.of(place));
        when(placeService.update(place)).thenReturn(place2);

        mockMvc.perform(put("/places/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(place2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.city", is("Moscow")))
                .andExpect(jsonPath("$.street", is("Vavilova")))
                .andExpect(jsonPath("$.building", is("100")))
                .andExpect(jsonPath("$.room", is(188)));
    }

    @Test
    @DisplayName("Delete a place")
    void deletePlace() throws Exception {
        when(placeService.findById(1L)).thenReturn(Optional.of(place));
        when(placeService.delete(place)).thenReturn(true);

        mockMvc.perform(delete("/places/{id}", 1))
                .andExpect(status().isOk());
    }

    static String toJSON(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}