package com.krylova.repository;

import com.krylova.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository  extends JpaRepository<Faculty, Long> {
    Faculty findByEmail(String email);
}
