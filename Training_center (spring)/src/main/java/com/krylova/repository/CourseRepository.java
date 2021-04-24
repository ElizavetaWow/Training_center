package com.krylova.repository;

import com.krylova.entity.Course;
import com.krylova.entity.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository  extends JpaRepository<Course, Long> {
    List<Course> findByCourseInfoName(String name);
}
