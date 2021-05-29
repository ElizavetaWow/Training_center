package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Company;
import com.krylova.entity.Employee;
import com.krylova.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

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
        employee1.setCompany(null);
        employee1.setCourses(null);
    }

    @Test
    @DisplayName("Create an employee")
    void create() throws Exception {
        when(employeeService.create(any())).thenReturn(employee);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(employee)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all employees")
    void findAll() throws Exception {
        when(employeeService.findAll()).thenReturn(List.of(employee, employee1));
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")))
                .andExpect(jsonPath("$[0].company.id", is(1)))
                .andExpect(jsonPath("$[0].company.account", is("123456789")))
                .andExpect(jsonPath("$[0].company.money", is(1000)))
                .andExpect(jsonPath("$[0].company.name", is("Romashka")))
                .andExpect(jsonPath("$[0].courses", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Alisa")))
                .andExpect(jsonPath("$[1].lastName", is("Batmanova")))
                .andExpect(jsonPath("$[1].email", is("ab@mail.ru")))
                .andExpect(jsonPath("$[1].password", is("123456789")))
                .andExpect(jsonPath("$[1].birthday", is("2001-02-03")))
                .andExpect(jsonPath("$[1].company",  is(nullValue())))
                .andExpect(jsonPath("$[1].courses", is(nullValue())));
    }

    @Test
    @DisplayName("Count employees by company")
    void countByCompany() throws Exception {
        when(employeeService.countByCompany(1L)).thenReturn(1);
        mockMvc.perform(get("/employees/comp_id_1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(1)));
    }

    @Test
    @DisplayName("Find an employee by ID")
    void find() throws Exception {
        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Liza")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")))
                .andExpect(jsonPath("$.company.id", is(1)))
                .andExpect(jsonPath("$.company.account", is("123456789")))
                .andExpect(jsonPath("$.company.money", is(1000)))
                .andExpect(jsonPath("$.company.name", is("Romashka")))
                .andExpect(jsonPath("$.courses", is(nullValue())));
    }

    @Test
    @DisplayName("Find employees by company")
    void findByCompany() throws Exception {
        when(employeeService.findByCompanyName("Romashka")).thenReturn(List.of(employee));
        mockMvc.perform(get("/employees/comp_Romashka"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")))
                .andExpect(jsonPath("$[0].company.id", is(1)))
                .andExpect(jsonPath("$[0].company.account", is("123456789")))
                .andExpect(jsonPath("$[0].company.money", is(1000)))
                .andExpect(jsonPath("$[0].company.name", is("Romashka")))
                .andExpect(jsonPath("$[0].courses", is(nullValue())));
    }

    @Test
    @DisplayName("Find an employee by email")
    void findByEmail() throws Exception {
        when(employeeService.findByEmail("liza@mail.ru")).thenReturn(List.of(employee));
        mockMvc.perform(get("/employees/email_liza@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")))
                .andExpect(jsonPath("$[0].company.id", is(1)))
                .andExpect(jsonPath("$[0].company.account", is("123456789")))
                .andExpect(jsonPath("$[0].company.money", is(1000)))
                .andExpect(jsonPath("$[0].company.name", is("Romashka")))
                .andExpect(jsonPath("$[0].courses", is(nullValue())));
    }

    @Test
    @DisplayName("Update an employee")
    void updateEmployee() throws Exception {
        Employee employee2 = employee;
        employee2.setFirstName("Vera");
        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeService.update(employee)).thenReturn(employee2);

        mockMvc.perform(put("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(employee2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Vera")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")))
                .andExpect(jsonPath("$.company.id", is(1)))
                .andExpect(jsonPath("$.company.account", is("123456789")))
                .andExpect(jsonPath("$.company.money", is(1000)))
                .andExpect(jsonPath("$.company.name", is("Romashka")))
                .andExpect(jsonPath("$.courses", is(nullValue())));
    }

    @Test
    @DisplayName("Delete an employee")
    void deleteEmployee() throws Exception {
        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeService.delete(employee)).thenReturn(true);

        mockMvc.perform(delete("/employees/{id}", 1))
                .andExpect(status().isOk());
    }

    static String toJSON(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}