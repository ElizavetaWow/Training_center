package com.krylova.service;

import com.krylova.entity.Place;
import com.krylova.repository.PlaceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlaceServiceTest {

    @MockBean
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceService placeService;

    Place place = new Place();
    Place place1 = new Place();

    @BeforeEach
    void setUp() {
        place.setId(1L);
        place.setCity("Moscow");
        place.setStreet("Vavilova");
        place.setBuilding("100");
        place.setRoom(14);
        place.setTimetables(null);

        place.setId(2L);
        place.setCity("Moscow");
        place.setStreet("Vavilova");
        place.setBuilding("58K");
        place.setRoom(2);
        place.setTimetables(null);
    }

    @Test
    @DisplayName("Create an place")
    void create() {
        when(placeRepository.save(place)).thenReturn(place);
        Place actual = placeService.create(place);
        Assert.assertEquals(place, actual);
    }

    @Test
    @DisplayName("Update a place")
    void update() {
        place.setRoom(6);
        when(placeRepository.save(place)).thenReturn(place);
        Place actual = placeService.update(place);
        Assert.assertEquals(place, actual);
    }

    @Test
    @DisplayName("Delete a place")
    void delete() {
        doNothing().when(placeRepository).delete(place);
        boolean actual = placeService.delete(place);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all places")
    void findAll() {
        when(placeRepository.findAll()).thenReturn(List.of(place, place1));
        List<Place> actual = placeService.findAll();
        Assert.assertEquals(List.of(place, place1), actual);
    }

    @Test
    @DisplayName("Find a place by ID")
    void findById() {
        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));
        Optional<Place> actual = placeService.findById(1L);
        Assert.assertEquals(Optional.of(place), actual);
        when(placeRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Place> actual1 = placeService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find places by city")
    void findByCity() {
        when(placeRepository.findByCity("Moscow")).thenReturn(List.of(place, place1));
        List<Place> actual = placeService.findByCity("Moscow");
        Assert.assertEquals(List.of(place, place1), actual);
        when(placeRepository.findByCity("Moscow1")).thenReturn(List.of());
        List<Place> actual1 = placeService.findByCity("Moscow1");
        Assert.assertEquals(List.of(), actual1);
    }
}