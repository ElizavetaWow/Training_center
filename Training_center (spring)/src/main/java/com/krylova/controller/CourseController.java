package com.krylova.controller;

import com.krylova.entity.Course;
import com.krylova.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/api/courses")
    public ResponseEntity<?> create(@RequestBody Course course){
        courseService.create(course);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/courses")
    public ResponseEntity<List<Course>> findAll(){
        final List<Course> courseList = courseService.findAll();
        return courseList != null && !courseList.isEmpty()
                ? new ResponseEntity<>(courseList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<Optional<Course>> find(@PathVariable(name = "id") Long id){
        final Optional<Course> course = courseService.find(id);
        return course != null
                ? new ResponseEntity<>(course, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable(name = "id") Long id, @RequestBody Course courseUpdate) {
        return courseService.find(id).map(course -> {
            course.setStartDate(courseUpdate.getStartDate());
            course.setFinishDate(courseUpdate.getFinishDate());
            course.setFaculty(courseUpdate.getFaculty());
            course.setCourseInfo(courseUpdate.getCourseInfo());
            course.setEmployees(courseUpdate.getEmployees());
            courseService.update(course);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long id) {
        return courseService.find(id).map(course -> {
            courseService.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
