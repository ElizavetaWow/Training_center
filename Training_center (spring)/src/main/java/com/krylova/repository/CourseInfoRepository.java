package com.krylova.repository;

import com.krylova.entity.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseInfoRepository  extends JpaRepository<CourseInfo, Long> {
    List<CourseInfo> findByName(String name);
}
