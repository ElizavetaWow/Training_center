package com.krylova.service;

import com.krylova.entity.CourseInfo;
import com.krylova.repository.CourseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseInfoService {

    @Autowired
    private CourseInfoRepository courseInfoRepository;

    public CourseInfo create(CourseInfo courseInfo) {
        return courseInfoRepository.save(courseInfo);
    }

    public CourseInfo update(CourseInfo courseInfo) {
        return courseInfoRepository.save(courseInfo);
    }

    public boolean delete(CourseInfo courseInfo) {
        courseInfoRepository.delete(courseInfo);
        return true;
    }

    public List<CourseInfo> findAll() {
        return courseInfoRepository.findAll();
    }

    public Optional<CourseInfo> findById(Long id) {
        return courseInfoRepository.findById(id);
    }

    public List<CourseInfo> findByName(String name) {
        return courseInfoRepository.findByName(name);
    }
}
