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
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<?> create(@RequestBody Course course) {
        courseService.create(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAll() {
        final List<Course> courseList = courseService.findAll();
        return courseList != null && !courseList.isEmpty()
                ? new ResponseEntity<>(courseList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Optional<Course>> findById(@PathVariable(name = "id") Long id) {
        final Optional<Course> course = courseService.findById(id);
        return course != null
                ? new ResponseEntity<>(course, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/courses/ci_{courseInfo}")
    public ResponseEntity<List<Course>> findByCourseInfo(@PathVariable(name = "courseInfo") String courseInfo) {
        final List<Course> courseList = courseService.findByCourseInfo(courseInfo);
        return courseList != null && !courseList.isEmpty()
                ? new ResponseEntity<>(courseList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable(name = "id") Long id, @RequestBody Course courseUpdate) {
        return courseService.findById(id).map(course -> {
            course.setStartDate(courseUpdate.getStartDate());
            course.setFinishDate(courseUpdate.getFinishDate());
            course.setPrice(courseUpdate.getPrice());
            course.setFaculty(courseUpdate.getFaculty());
            course.setCourseInfo(courseUpdate.getCourseInfo());
            course.setEmployees(courseUpdate.getEmployees());
            courseService.update(course);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long id) {
        return courseService.findById(id).map(course -> {
            courseService.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
