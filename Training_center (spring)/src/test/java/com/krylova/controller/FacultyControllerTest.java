package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Faculty;
import com.krylova.service.FacultyService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FacultyControllerTest {

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private MockMvc mockMvc;

    Faculty faculty = new Faculty();
    Faculty faculty1 = new Faculty();

    @BeforeEach
    void setUp() {
        faculty.setId(1L);
        faculty.setFirstName("Liza");
        faculty.setLastName("Ivanova");
        faculty.setEmail("liza@mail.ru");
        faculty.setPassword("123456789");
        faculty.setBirthday(Date.valueOf("1978-02-03"));
        faculty.setCourses(null);

        faculty1.setId(2L);
        faculty1.setFirstName("Alisa");
        faculty1.setLastName("Batmanova");
        faculty1.setEmail("ab@mail.ru");
        faculty1.setPassword("123456789");
        faculty1.setBirthday(Date.valueOf("2001-02-03"));
        faculty1.setCourses(null);
    }

    @Test
    @DisplayName("Create an faculty")
    void create() throws Exception {
        when(facultyService.create(any())).thenReturn(faculty);
        mockMvc.perform(post("/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(faculty)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all faculties")
    void findAll() throws Exception {
        when(facultyService.findAll()).thenReturn(List.of(faculty, faculty1));
        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Alisa")))
                .andExpect(jsonPath("$[1].lastName", is("Batmanova")))
                .andExpect(jsonPath("$[1].email", is("ab@mail.ru")))
                .andExpect(jsonPath("$[1].password", is("123456789")))
                .andExpect(jsonPath("$[1].birthday", is("2001-02-03")));
    }


    @Test
    @DisplayName("Find a faculty by ID")
    void find() throws Exception {
        when(facultyService.findById(1L)).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Liza")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Find faculties by email")
    void findByEmail() throws Exception {
        when(facultyService.findByEmail("liza@mail.ru")).thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculties/email_liza@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Update a faculty")
    void updateFaculty() throws Exception {
        Faculty faculty2 = faculty;
        faculty2.setFirstName("Vera");
        when(facultyService.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyService.update(faculty)).thenReturn(faculty2);

        mockMvc.perform(put("/faculties/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(faculty2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Vera")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Delete a faculty")
    void deleteFaculty() throws Exception {
        when(facultyService.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyService.delete(faculty)).thenReturn(true);

        mockMvc.perform(delete("/faculties/{id}", 1))
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