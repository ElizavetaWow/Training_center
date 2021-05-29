package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Course;
import com.krylova.entity.CourseInfo;
import com.krylova.entity.Timetable;
import com.krylova.service.TimetableService;
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
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TimetableControllerTest {

    @MockBean
    private TimetableService timetableService;

    @Autowired
    private MockMvc mockMvc;

    Timetable timetable = new Timetable();
    Timetable timetable1 = new Timetable();
    Course course = new Course();
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

        timetable.setId(1L);
        timetable.setDate(Date.valueOf("2021-01-15"));
        timetable.setTime(Time.valueOf("15:30:00"));
        timetable.setCourse(course);
        timetable.setPlace(null);

        timetable1.setId(2L);
        timetable1.setDate(Date.valueOf("2021-01-15"));
        timetable1.setTime(Time.valueOf("21:00:00"));
        timetable1.setCourse(course);
        timetable1.setPlace(null);
    }

    @Test
    @DisplayName("Create a timetable")
    void create() throws Exception {
        when(timetableService.create(any())).thenReturn(timetable);
        System.out.println(toJSON(timetable));
        mockMvc.perform(post("/timetables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(timetable)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all timetables")
    void findAll() throws Exception {
        when(timetableService.findAll()).thenReturn(List.of(timetable, timetable1));
        mockMvc.perform(get("/timetables"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2021-01-15")))
                .andExpect(jsonPath("$[0].time", is("15:30:00")))
                .andExpect(jsonPath("$[0].course.id", is(1)))
                .andExpect(jsonPath("$[0].course.price", is(1000)))
                .andExpect(jsonPath("$[0].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[0].place", is(nullValue())))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].date", is("2021-01-15")))
                .andExpect(jsonPath("$[1].time", is("21:00:00")))
                .andExpect(jsonPath("$[1].course.id", is(1)))
                .andExpect(jsonPath("$[1].course.price", is(1000)))
                .andExpect(jsonPath("$[1].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[1].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[1].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[1].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[1].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[1].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[1].place", is(nullValue())));
    }


    @Test
    @DisplayName("Find a timetable by ID")
    void find() throws Exception {
        when(timetableService.findById(1L)).thenReturn(Optional.of(timetable));
        mockMvc.perform(get("/timetables/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("2021-01-15")))
                .andExpect(jsonPath("$.time", is("15:30:00")))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.course.price", is(1000)))
                .andExpect(jsonPath("$.course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$.course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$.course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$.course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$.course.faculty", is(nullValue())))
                .andExpect(jsonPath("$.course.employees", is(nullValue())))
                .andExpect(jsonPath("$.place", is(nullValue())));
    }

    @Test
    @DisplayName("Find timetables by name")
    void findByName() throws Exception {
        when(timetableService.findByName("Maths")).thenReturn(List.of(timetable, timetable1));
        mockMvc.perform(get("/timetables/cn_Maths"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2021-01-15")))
                .andExpect(jsonPath("$[0].time", is("15:30:00")))
                .andExpect(jsonPath("$[0].course.id", is(1)))
                .andExpect(jsonPath("$[0].course.price", is(1000)))
                .andExpect(jsonPath("$[0].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[0].place", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].date", is("2021-01-15")))
                .andExpect(jsonPath("$[1].time", is("21:00:00")))
                .andExpect(jsonPath("$[1].course.id", is(1)))
                .andExpect(jsonPath("$[1].course.price", is(1000)))
                .andExpect(jsonPath("$[1].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[1].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[1].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[1].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[1].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[1].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[1].place", is(nullValue())));
    }

    @Test
    @DisplayName("Find timetables by date")
    void findByDate() throws Exception {
        when(timetableService.findByDate(Date.valueOf("2021-01-15"))).thenReturn(List.of(timetable, timetable1));
        mockMvc.perform(get("/timetables/date_2021-01-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2021-01-15")))
                .andExpect(jsonPath("$[0].time", is("15:30:00")))
                .andExpect(jsonPath("$[0].course.id", is(1)))
                .andExpect(jsonPath("$[0].course.price", is(1000)))
                .andExpect(jsonPath("$[0].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[0].place", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].date", is("2021-01-15")))
                .andExpect(jsonPath("$[1].time", is("21:00:00")))
                .andExpect(jsonPath("$[1].course.id", is(1)))
                .andExpect(jsonPath("$[1].course.price", is(1000)))
                .andExpect(jsonPath("$[1].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[1].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[1].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[1].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[1].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[1].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[1].place", is(nullValue())));
    }

    @Test
    @DisplayName("Find timetables by name and date")
    void findByNameAndDate() throws Exception {
        when(timetableService.findByNameAndDate("Maths",Date.valueOf("2021-01-15"))).thenReturn(List.of(timetable, timetable1));
        mockMvc.perform(get("/timetables/cn_Maths/date_2021-01-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2021-01-15")))
                .andExpect(jsonPath("$[0].time", is("15:30:00")))
                .andExpect(jsonPath("$[0].course.id", is(1)))
                .andExpect(jsonPath("$[0].course.price", is(1000)))
                .andExpect(jsonPath("$[0].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[0].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[0].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[0].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[0].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[0].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[0].place", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].date", is("2021-01-15")))
                .andExpect(jsonPath("$[1].time", is("21:00:00")))
                .andExpect(jsonPath("$[1].course.id", is(1)))
                .andExpect(jsonPath("$[1].course.price", is(1000)))
                .andExpect(jsonPath("$[1].course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$[1].course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$[1].course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$[1].course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$[1].course.faculty", is(nullValue())))
                .andExpect(jsonPath("$[1].course.employees", is(nullValue())))
                .andExpect(jsonPath("$[1].place", is(nullValue())));
    }

    @Test
    @DisplayName("Update a timetable")
    void updateTimetable() throws Exception {
        Timetable timetable2 = timetable;
        timetable2.setDate(Date.valueOf("2021-01-13"));
        when(timetableService.findById(1L)).thenReturn(Optional.of(timetable));
        when(timetableService.update(timetable)).thenReturn(timetable2);

        mockMvc.perform(put("/timetables/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(timetable2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("2021-01-13")))
                .andExpect(jsonPath("$.time", is("15:30:00")))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.course.price", is(1000)))
                .andExpect(jsonPath("$.course.startDate", is("2020-02-03")))
                .andExpect(jsonPath("$.course.finishDate", is("2021-02-03")))
                .andExpect(jsonPath("$.course.courseInfo.id", is(1)))
                .andExpect(jsonPath("$.course.courseInfo.name", is("Maths")))
                .andExpect(jsonPath("$.course.faculty", is(nullValue())))
                .andExpect(jsonPath("$.course.employees", is(nullValue())))
                .andExpect(jsonPath("$.place", is(nullValue())));
    }

    @Test
    @DisplayName("Delete a timetable")
    void deleteTimetable() throws Exception {
        when(timetableService.findById(1L)).thenReturn(Optional.of(timetable));
        when(timetableService.delete(timetable)).thenReturn(true);

        mockMvc.perform(delete("/timetables/{id}", 1))
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