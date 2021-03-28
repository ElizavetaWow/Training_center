package com.krylova.repository;

import com.krylova.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository  extends JpaRepository<Faculty, Long> {
}
