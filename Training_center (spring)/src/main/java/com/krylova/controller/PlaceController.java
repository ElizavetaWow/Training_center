package com.krylova.controller;

import com.krylova.entity.Place;
import com.krylova.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @PostMapping("/places")
    public ResponseEntity<?> create(@RequestBody Place place){
        placeService.create(place);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/places")
    public ResponseEntity<List<Place>> findAll(){
        final List<Place> placeList = placeService.findAll();
        return placeList != null && !placeList.isEmpty()
                ? new ResponseEntity<>(placeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/places/{id}")
    public ResponseEntity<Optional<Place>> find(@PathVariable(name = "id") Long id){
        final Optional<Place> place = placeService.find(id);
        return place != null
                ? new ResponseEntity<>(place, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/places/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable(name = "id") Long id, @RequestBody Place placeUpdate) {
        return placeService.find(id).map(place -> {
            place.setCity(placeUpdate.getCity());
            place.setStreet(placeUpdate.getStreet());
            place.setBuilding(placeUpdate.getBuilding());
            place.setRoom(placeUpdate.getRoom());
            place.setTimetables(placeUpdate.getTimetables());
            placeService.update(place);
            return new ResponseEntity<>(place, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/places/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable(name = "id") Long id) {
        return placeService.find(id).map(place -> {
            placeService.delete(place);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
