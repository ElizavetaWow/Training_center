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

    public void create(Course course){
        courseRepository.save(course);
    }

    public void update(Course course){
        courseRepository.save(course);
    }

    public void delete(Course course){
        courseRepository.delete(course);
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Optional<Course> find(Long id){
        return courseRepository.findById(id);
    }

}
