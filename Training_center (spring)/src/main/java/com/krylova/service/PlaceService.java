package com.krylova.service;

import com.krylova.entity.Place;
import com.krylova.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public Place create(Place place) {
        return placeRepository.save(place);
    }

    public Place update(Place place) {
        return placeRepository.save(place);
    }

    public boolean delete(Place place) {
        placeRepository.delete(place);
        return true;
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }

    public List<Place> findByCity(String city) {
        return placeRepository.findByCity(city);
    }

}
