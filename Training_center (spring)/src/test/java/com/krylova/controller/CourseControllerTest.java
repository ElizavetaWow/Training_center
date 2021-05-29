package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Course;
import com.krylova.entity.CourseInfo;
import com.krylova.service.CourseService;
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
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    Course course = new Course();
    Course course1 = new Course();
    CourseInfo courseInfo = new CourseInfo();

    @BeforeEach
    void setUp() {
        courseInfo.setId(1L);
        courseInfo.setName("Maths");

        course.setId(1L);
        course.setPrice(1000);
        course.setStartDate(Date.valueOf("2020-02-03"));
        course.setFinishDate(Date.valueOf("2021-02-03"));
        course.setCourseInfo(courseInfo);
        course.setFaculty(null);
        course.setEmployees(null);

        course1.setId(2L);
        course1.setPrice(1500);
        course1.setStartDate(Date.valueOf("2020-10-03"));
        course1.setFinishDate(Date.valueOf("2021-02-03"));
        course1.setCourseInfo(null);
        course1.setFaculty(null);
        course1.setEmployees(null);
    }

    @Test
    @DisplayName("Create a course")
    void create() throws Exception {
        when(courseService.create(any())).thenReturn(course);
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(course)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all courses")
    void findAll() throws Exception {
        when(courseService.findAll()).thenReturn(List.of(course, course1));
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1000)))
                .andExpect(jsonPath("$[0].startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].employees", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].price", is(1500)))
                .andExpect(jsonPath("$[1].startDate", is("2020-10-03")))
                .andExpect(jsonPath("$[1].finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[1].courseInfo", is(nullValue())))
                .andExpect(jsonPath("$[1].faculty", is(nullValue())))
                .andExpect(jsonPath("$[1].employees", is(nullValue())));
    }


    @Test
    @DisplayName("Find a course by ID")
    void find() throws Exception {
        when(courseService.findById(1L)).thenReturn(Optional.of(course));
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.price", is(1000)))
                .andExpect(jsonPath("$.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$.courseInfo.id", is(1)))
                .andExpect(jsonPath("$.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$.faculty", is(nullValue())))
                .andExpect(jsonPath("$.employees", is(nullValue())));
    }

    @Test
    @DisplayName("Find courses by courseInfo")
    void findByCompany() throws Exception {
        when(courseService.findByCourseInfo("Maths")).thenReturn(List.of(course));
        mockMvc.perform(get("/courses/ci_Maths"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1000)))
                .andExpect(jsonPath("$[0].startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].employees", is(nullValue())));
    }


    @Test
    @DisplayName("Update a course")
    void updateCourse() throws Exception {
        Course course2 = course;
        course2.setStartDate(Date.valueOf("2020-02-13"));
        when(courseService.findById(1L)).thenReturn(Optional.of(course));
        when(courseService.update(course)).thenReturn(course2);

        mockMvc.perform(put("/courses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(course2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.price", is(1000)))
                .andExpect(jsonPath("$.startDate", is("2020-02-13")))
                .andExpect(jsonPath("$.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$.courseInfo.id", is(1)))
                .andExpect(jsonPath("$.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$.faculty", is(nullValue())))
                .andExpect(jsonPath("$.employees", is(nullValue())));
    }

    @Test
    @DisplayName("Delete a course")
    void deleteCourse() throws Exception {
        when(courseService.findById(1L)).thenReturn(Optional.of(course));
        when(courseService.delete(course)).thenReturn(true);

        mockMvc.perform(delete("/courses/{id}", 1))
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