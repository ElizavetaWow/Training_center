package com.krylova.repository;

import com.krylova.entity.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInfoRepository  extends JpaRepository<CourseInfo, Long> {
}
