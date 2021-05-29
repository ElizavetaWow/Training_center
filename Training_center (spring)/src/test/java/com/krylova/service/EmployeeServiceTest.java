package com.krylova.service;

import com.krylova.entity.Company;
import com.krylova.entity.Employee;
import com.krylova.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    Employee employee = new Employee();
    Employee employee1 = new Employee();
    Company company = new Company();

    @BeforeEach
    void setUp() {
        company.setId(1L);
        company.setAccount("123456789");
        company.setMoney(1000);
        company.setName("Romashka");

        employee.setId(1L);
        employee.setFirstName("Liza");
        employee.setLastName("Ivanova");
        employee.setEmail("liza@mail.ru");
        employee.setPassword("123456789");
        employee.setBirthday(Date.valueOf("1978-02-03"));
        employee.setCompany(company);
        employee.setCourses(null);

        employee1.setId(2L);
        employee1.setFirstName("Alisa");
        employee1.setLastName("Batmanova");
        employee1.setEmail("ab@mail.ru");
        employee1.setPassword("123456789");
        employee1.setBirthday(Date.valueOf("2001-02-03"));
        employee1.setCompany(company);
        employee1.setCourses(null);
    }

    @Test
    @DisplayName("Create an employee")
    void create() {
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee actual = employeeService.create(employee);
        Assert.assertEquals(employee, actual);
    }

    @Test
    @DisplayName("Update an employee")
    void update() {
        employee.setPassword("987654321");
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee actual = employeeService.update(employee);
        Assert.assertEquals(employee, actual);
    }

    @Test
    @DisplayName("Delete an employee")
    void delete() {
        doNothing().when(employeeRepository).delete(employee);
        boolean actual = employeeService.delete(employee);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all employees")
    void findAll() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee, employee1));
        List<Employee> actual = employeeService.findAll();
        Assert.assertEquals(List.of(employee, employee1), actual);
    }

    @Test
    @DisplayName("Find an employee by ID")
    void findById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> actual = employeeService.findById(1L);
        Assert.assertEquals(Optional.of(employee), actual);
        when(employeeRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Employee> actual1 = employeeService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find an employee by company name")
    void findByCompanyName() {
        when(employeeRepository.findByCompanyName("Romashka")).thenReturn(List.of(employee, employee1));
        List<Employee> actual = employeeService.findByCompanyName("Romashka");
        Assert.assertEquals(List.of(employee, employee1), actual);
        when(employeeRepository.findByCompanyName("Romashka1")).thenReturn(List.of());
        List<Employee> actual1 = employeeService.findByCompanyName("Romashka1");
        Assert.assertEquals(List.of(), actual1);
    }

    @Test
    @DisplayName("Find employees by email")
    void findByEmail() {
        when(employeeRepository.findByEmail("liza@mail.ru")).thenReturn(List.of(employee));
        List<Employee> actual = employeeService.findByEmail("liza@mail.ru");
        Assert.assertEquals(List.of(employee), actual);
        when(employeeRepository.findByEmail("lizochka@mail.ru")).thenReturn(List.of());
        List<Employee> actual1 = employeeService.findByEmail("lizochka@mail.ru");
        Assert.assertEquals(List.of(), actual1);
    }

    @Test
    @DisplayName("Count employees by company name")
    void countByCompany() {
        when(employeeRepository.countEmployeeByCompanyId(1L)).thenReturn(2);
        Integer actual = employeeService.countByCompany(1L);
        Assert.assertEquals(Integer.valueOf(2), actual);
        when(employeeRepository.countEmployeeByCompanyId(22L)).thenReturn(0);
        Integer actual1 = employeeService.countByCompany(22L);
        Assert.assertEquals(Integer.valueOf(0), actual1);
    }
}