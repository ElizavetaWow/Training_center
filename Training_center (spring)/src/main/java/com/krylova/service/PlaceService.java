package com.krylova.service;

import com.krylova.entity.Place;
import com.krylova.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService{

    @Autowired
    private PlaceRepository placeRepository;

    public void create(Place place){
        placeRepository.save(place);
    }

    public void update(Place place){
        placeRepository.save(place);
    }

    public void delete(Place place){
        placeRepository.delete(place);
    }

    public List<Place> findAll(){
        return placeRepository.findAll();
    }

    public Optional<Place> find(Long id){
        return placeRepository.findById(id);
    }
    
}
