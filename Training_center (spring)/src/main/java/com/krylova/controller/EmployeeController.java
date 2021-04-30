package com.krylova.controller;

import com.krylova.entity.Course;
import com.krylova.entity.Employee;
import com.krylova.service.CourseService;
import com.krylova.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<?> create(@RequestBody Employee employee){
        employeeService.create(employee);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll(){
        final List<Employee> employeeList = employeeService.findAll();
        return employeeList != null && !employeeList.isEmpty()
                ? new ResponseEntity<>(employeeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/comp_id_{id}")
    public ResponseEntity<Integer> countByCompany(@PathVariable(name = "id") Long id){
        final Integer employeesNumber = employeeService.countByCompany(id);
        return employeesNumber != null
                ? new ResponseEntity<>(employeesNumber, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable(name = "id") Long id){
        final Optional<Employee> employee = employeeService.findById(id);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/comp_{companyName}")
    public ResponseEntity<List<Employee>> findByCompany(@PathVariable(name = "companyName") String companyName){
        final List<Employee> employeeList = employeeService.findByCompanyName(companyName);
        return employeeList != null && !employeeList.isEmpty()
                ? new ResponseEntity<>(employeeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/email_{email}")
    public ResponseEntity<List<Employee>> findByEmail(@PathVariable(name = "email") String email){
        final List<Employee> employeeList = employeeService.findByEmail(email);
        return employeeList != null && !employeeList.isEmpty()
                ? new ResponseEntity<>(employeeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employeeUpdate) {
        return employeeService.findById(id).map(employee -> {
            employee.setFirstName(employeeUpdate.getFirstName());
            employee.setLastName(employeeUpdate.getLastName());
            employee.setEmail(employeeUpdate.getEmail());
            employee.setPassword(employeeUpdate.getPassword());
            employee.setBirthday(employeeUpdate.getBirthday());
            employee.setCompany(employeeUpdate.getCompany());
            employee.setCourses(employeeUpdate.getCourses());
            employeeService.update(employee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") Long id) {
        return employeeService.findById(id).map(employee -> {
            employeeService.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
