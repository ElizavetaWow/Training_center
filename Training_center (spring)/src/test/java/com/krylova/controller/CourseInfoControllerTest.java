package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.CourseInfo;
import com.krylova.service.CourseInfoService;
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
class CourseInfoControllerTest {

    @MockBean
    private CourseInfoService courseInfoService;

    @Autowired
    private MockMvc mockMvc;

    CourseInfo courseInfo = new CourseInfo();
    CourseInfo courseInfo1 = new CourseInfo();

    @BeforeEach
    void setUp() {
        courseInfo.setId(1L);
        courseInfo.setName("Maths");

        courseInfo1.setId(2L);
        courseInfo1.setName("Russian");
    }

    @Test
    @DisplayName("Create an courseInfo")
    void create() throws Exception {
        when(courseInfoService.create(any())).thenReturn(courseInfo);
        mockMvc.perform(post("/courseInfos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(courseInfo)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all courseInfos")
    void findAll() throws Exception {
        when(courseInfoService.findAll()).thenReturn(List.of(courseInfo, courseInfo1));
        mockMvc.perform(get("/courseInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Maths")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Russian")));
    }

    @Test
    @DisplayName("Find a courseInfo by ID")
    void find() throws Exception {
        when(courseInfoService.findById(1L)).thenReturn(Optional.of(courseInfo));
        mockMvc.perform(get("/courseInfos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Maths")));
    }


    @Test
    @DisplayName("Find a courseInfo by name")
    void findByEmail() throws Exception {
        when(courseInfoService.findByName("Maths")).thenReturn(List.of(courseInfo));
        mockMvc.perform(get("/courseInfos/n_Maths"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Maths")));
    }

    @Test
    @DisplayName("Update a courseInfo")
    void updateCourseInfo() throws Exception {
        CourseInfo courseInfo2 = courseInfo;
        courseInfo2.setName("English");
        when(courseInfoService.findById(1L)).thenReturn(Optional.of(courseInfo));
        when(courseInfoService.update(courseInfo)).thenReturn(courseInfo2);

        mockMvc.perform(put("/courseInfos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(courseInfo2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("English")));
    }

    @Test
    @DisplayName("Delete a courseInfo")
    void deleteCourseInfo() throws Exception {
        when(courseInfoService.findById(1L)).thenReturn(Optional.of(courseInfo));
        when(courseInfoService.delete(courseInfo)).thenReturn(true);

        mockMvc.perform(delete("/courseInfos/{id}", 1))
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