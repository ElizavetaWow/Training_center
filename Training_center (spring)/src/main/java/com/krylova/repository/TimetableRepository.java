package com.krylova.repository;

import com.krylova.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository  extends JpaRepository<Timetable, Long> {
}
