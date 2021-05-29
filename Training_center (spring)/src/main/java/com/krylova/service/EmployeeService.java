package com.krylova.service;

import com.krylova.entity.Employee;
import com.krylova.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean delete(Employee employee) {
        employeeRepository.delete(employee);
        return true;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByCompanyName(String companyName) {
        return employeeRepository.findByCompanyName(companyName);
    }

    public List<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Integer countByCompany(Long id) {
        return employeeRepository.countEmployeeByCompanyId(id);
    }

}
