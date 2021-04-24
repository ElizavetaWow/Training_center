package com.krylova.service;

import com.krylova.entity.Company;
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

    public void create(CourseInfo courseInfo){
        courseInfoRepository.save(courseInfo);
    }

    public void update(CourseInfo courseInfo){
        courseInfoRepository.save(courseInfo);
    }

    public void delete(CourseInfo courseInfo){
        courseInfoRepository.delete(courseInfo);
    }

    public List<CourseInfo> findAll(){
        return courseInfoRepository.findAll();
    }

    public Optional<CourseInfo> findById(Long id){
        return courseInfoRepository.findById(id);
    }

    public List<CourseInfo> findByName(String name){
        return courseInfoRepository.findByName(name);
    }
}
