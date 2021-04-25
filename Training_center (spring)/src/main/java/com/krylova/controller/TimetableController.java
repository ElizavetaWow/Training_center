package com.krylova.controller;

import com.krylova.DateUtil;
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

    @PostMapping("/timetables")
    public ResponseEntity<?> create(@RequestBody Timetable timetable){
        timetableService.create(timetable);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/timetables")
    public ResponseEntity<List<Timetable>> findAll(){
        final List<Timetable> timetableList = timetableService.findAll();
        return timetableList != null && !timetableList.isEmpty()
                ? new ResponseEntity<>(timetableList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timetables/{id}")
    public ResponseEntity<Optional<Timetable>> findById(@PathVariable(name = "id") Long id){
        final Optional<Timetable> timetable = timetableService.findById(id);
        return timetable != null
                ? new ResponseEntity<>(timetable, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timetables/cn_{name}")
    public ResponseEntity<List<Timetable>> findByName(@PathVariable(name = "name") String name){
        final List<Timetable> timetableList = timetableService.findByName(name);
        return timetableList != null && !timetableList.isEmpty()
                ? new ResponseEntity<>(timetableList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timetables/date_{date}")
    public ResponseEntity<List<Timetable>> findByDate(@PathVariable(name = "date") String date){
        final List<Timetable> timetableList = timetableService.findByDate(DateUtil.parse(date));
        return timetableList != null && !timetableList.isEmpty()
                ? new ResponseEntity<>(timetableList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/timetables/cn_{name}/date_{date}")
    public ResponseEntity<List<Timetable>> findByNameAndDate(@PathVariable(name = "name") String name,
                                                      @PathVariable(name = "date") String date){

        final List<Timetable> timetableList = timetableService.findByNameAndDate(name, DateUtil.parse(date));
        return timetableList != null && !timetableList.isEmpty()
                ? new ResponseEntity<>(timetableList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/timetables/{id}")
    public ResponseEntity<?> updateTimetable(@PathVariable(name = "id") Long id, @RequestBody Timetable timetableUpdate) {
        return timetableService.findById(id).map(timetable -> {
            timetable.setDate(timetableUpdate.getDate());
            timetable.setTime(timetableUpdate.getTime());
            timetable.setPlace(timetableUpdate.getPlace());
            timetable.setCourse(timetableUpdate.getCourse());
            timetableService.update(timetable);
            return new ResponseEntity<>(timetable, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/timetables/{id}")
    public ResponseEntity<?> deleteTimetable(@PathVariable(name = "id") Long id) {
        return timetableService.findById(id).map(timetable -> {
            timetableService.delete(timetable);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
