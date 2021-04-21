package com.krylova.repository;


import com.krylova.entity.Company;
import com.krylova.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyName(String companyName);
}
