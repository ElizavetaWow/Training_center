package com.krylova.repository;


import com.krylova.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyName(String companyName);
    List<Employee> findByEmail(String email);
    Integer countEmployeeByCompanyId(Long id);
}
