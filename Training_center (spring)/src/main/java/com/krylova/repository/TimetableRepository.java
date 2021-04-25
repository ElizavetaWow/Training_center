package com.krylova.repository;

import com.krylova.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimetableRepository  extends JpaRepository<Timetable, Long> {
    List<Timetable> findByCourse_CourseInfo_Name(String name);
    List<Timetable> findByDate(LocalDate date);
    List<Timetable> findByCourse_CourseInfo_NameAndDate(String name, LocalDate date);
}
