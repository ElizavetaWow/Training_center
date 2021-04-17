package com.krylova.controller;

import com.krylova.entity.CourseInfo;
import com.krylova.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseInfoController {

    private final CourseInfoService courseInfoService;

    @Autowired
    public CourseInfoController(CourseInfoService courseInfoService){
        this.courseInfoService = courseInfoService;
    }

    @PostMapping("/courseInfos")
    public ResponseEntity<?> create(@RequestBody CourseInfo courseInfo){
        courseInfoService.create(courseInfo);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/courseInfos")
    public ResponseEntity<List<CourseInfo>> findAll(){
        final List<CourseInfo> courseInfoList = courseInfoService.findAll();
        return courseInfoList != null && !courseInfoList.isEmpty()
                ? new ResponseEntity<>(courseInfoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/courseInfos/{id}")
    public ResponseEntity<Optional<CourseInfo>> find(@PathVariable(name = "id") Long id){
        final Optional<CourseInfo> courseInfo = courseInfoService.find(id);
        return courseInfo != null
                ? new ResponseEntity<>(courseInfo, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/courseInfos/{id}")
    public ResponseEntity<?> updateCourseInfo(@PathVariable(name = "id") Long id, @RequestBody CourseInfo courseInfoUpdate) {
        return courseInfoService.find(id).map(courseInfo -> {
            courseInfo.setName(courseInfoUpdate.getName());
            courseInfo.setCourses(courseInfoUpdate.getCourses());
            courseInfoService.update(courseInfo);
            return new ResponseEntity<>(courseInfo, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/courseInfos/{id}")
    public ResponseEntity<?> deleteCourseInfo(@PathVariable(name = "id") Long id) {
        return courseInfoService.find(id).map(courseInfo -> {
            courseInfoService.delete(courseInfo);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
