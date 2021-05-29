package com.krylova.service;

import com.krylova.entity.Course;
import com.krylova.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Course course) {
        return courseRepository.save(course);
    }

    public boolean delete(Course course) {
        courseRepository.delete(course);
        return true;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findByCourseInfo(String courseInfo) {
        return courseRepository.findByCourseInfoName(courseInfo);
    }

}
