package com.krylova.controller;

import com.krylova.entity.Faculty;
import com.krylova.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @PostMapping("/faculties")
    public ResponseEntity<?> create(@RequestBody Faculty faculty){
        facultyService.create(faculty);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/faculties")
    public ResponseEntity<List<Faculty>> findAll(){
        final List<Faculty> facultyList = facultyService.findAll();
        return facultyList != null && !facultyList.isEmpty()
                ? new ResponseEntity<>(facultyList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/faculties/{id}")
    public ResponseEntity<Optional<Faculty>> findById(@PathVariable(name = "id") Long id){
        final Optional<Faculty> faculty = facultyService.findById(id);
        return faculty != null
                ? new ResponseEntity<>(faculty, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/faculties/email_{email}")
    public ResponseEntity<List<Faculty>> findAll(@PathVariable(name = "email") String email){
        final List<Faculty> facultyList = facultyService.findByEmail(email);
        return facultyList != null && !facultyList.isEmpty()
                ? new ResponseEntity<>(facultyList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/faculties/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable(name = "id") Long id, @RequestBody Faculty facultyUpdate) {
        return facultyService.findById(id).map(faculty -> {
            faculty.setFirstName(facultyUpdate.getFirstName());
            faculty.setLastName(facultyUpdate.getLastName());
            faculty.setEmail(facultyUpdate.getEmail());
            faculty.setPassword(facultyUpdate.getPassword());
            faculty.setBirthday(facultyUpdate.getBirthday());
            faculty.setCourses(facultyUpdate.getCourses());
            facultyService.update(faculty);
            return new ResponseEntity<>(faculty, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable(name = "id") Long id) {
        return facultyService.findById(id).map(faculty -> {
            facultyService.delete(faculty);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
