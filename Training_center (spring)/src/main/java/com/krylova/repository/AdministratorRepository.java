package com.krylova.repository;


import com.krylova.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    List<Administrator> findByEmail(String email);
}
