package com.krylova.controller;

import com.krylova.entity.Timetable;
import com.krylova.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService){
        this.timetableService = timetableService;
    }

    @PostMapping("/api/timetables")
    public ResponseEntity<?> create(@RequestBody Timetable timetable){
        timetableService.create(timetable);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/timetables")
    public ResponseEntity<List<Timetable>> findAll(){
        final List<Timetable> timetableList = timetableService.findAll();
        return timetableList != null && !timetableList.isEmpty()
                ? new ResponseEntity<>(timetableList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/timetables/{id}")
    public ResponseEntity<Optional<Timetable>> find(@PathVariable(name = "id") Long id){
        final Optional<Timetable> timetable = timetableService.find(id);
        return timetable != null
                ? new ResponseEntity<>(timetable, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/timetables/{id}")
    public ResponseEntity<?> updateTimetable(@PathVariable(name = "id") Long id, @RequestBody Timetable timetableUpdate) {
        return timetableService.find(id).map(timetable -> {
            timetable.setDate(timetableUpdate.getDate());
            timetable.setTime(timetableUpdate.getTime());
            timetable.setPlace(timetableUpdate.getPlace());
            timetable.setCourse(timetableUpdate.getCourse());
            timetableService.update(timetable);
            return new ResponseEntity<>(timetable, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/timetables/{id}")
    public ResponseEntity<?> deleteTimetable(@PathVariable(name = "id") Long id) {
        return timetableService.find(id).map(timetable -> {
            timetableService.delete(timetable);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
