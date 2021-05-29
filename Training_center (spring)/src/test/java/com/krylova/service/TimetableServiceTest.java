package com.krylova.service;

import com.krylova.entity.Timetable;
import com.krylova.repository.TimetableRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class TimetableServiceTest {

    @MockBean
    private TimetableRepository timetableRepository;
    @Autowired
    private TimetableService timetableService;

    Timetable timetable = new Timetable();
    Timetable timetable1 = new Timetable();

    @BeforeEach
    void setUp() {
        timetable.setId(1L);
        timetable.setDate(Date.valueOf("2021-01-15"));
        timetable.setTime(Time.valueOf("15:30:00"));
        timetable.setCourse(null);
        timetable.setPlace(null);

        timetable1.setId(2L);
        timetable1.setDate(Date.valueOf("2021-01-15"));
        timetable1.setTime(Time.valueOf("21:00:00"));
        timetable1.setCourse(null);
        timetable1.setPlace(null);
    }

    @Test
    @DisplayName("Create a timetable")
    void create() {
        when(timetableRepository.save(timetable)).thenReturn(timetable);
        Timetable actual = timetableService.create(timetable);
        Assert.assertEquals(timetable, actual);
    }

    @Test
    @DisplayName("Update a timetable")
    void update() {
        timetable.setTime(Time.valueOf("16:30:00"));
        when(timetableRepository.save(timetable)).thenReturn(timetable);
        Timetable actual = timetableService.update(timetable);
        Assert.assertEquals(timetable, actual);
    }

    @Test
    @DisplayName("Delete a timetable")
    void delete() {
        doNothing().when(timetableRepository).delete(timetable);
        boolean actual = timetableService.delete(timetable);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all timetables")
    void findAll() {
        when(timetableRepository.findAll()).thenReturn(List.of(timetable, timetable1));
        List<Timetable> actual = timetableService.findAll();
        Assert.assertEquals(List.of(timetable, timetable1), actual);
    }

    @Test
    @DisplayName("Find a timetable by ID")
    void findById() {
        when(timetableRepository.findById(1L)).thenReturn(Optional.of(timetable));
        Optional<Timetable> actual = timetableService.findById(1L);
        Assert.assertEquals(Optional.of(timetable), actual);
        when(timetableRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Timetable> actual1 = timetableService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find a timetable by courseInfo name")
    void findByName() {
        when(timetableRepository.findByCourse_CourseInfo_Name("Maths")).thenReturn(List.of(timetable, timetable1));
        List<Timetable> actual = timetableService.findByName("Maths");
        Assert.assertEquals(List.of(timetable, timetable1), actual);
        when(timetableRepository.findByCourse_CourseInfo_Name("Maths1")).thenReturn(List.of());
        List<Timetable> actual1 = timetableService.findByName("Maths1");
        Assert.assertEquals(List.of(), actual1);
    }

    @Test
    @DisplayName("Find timetables by date")
    void findByDate() {
        when(timetableRepository.findByDate(Date.valueOf("2021-01-15"))).thenReturn(List.of(timetable, timetable1));
        List<Timetable> actual = timetableService.findByDate(Date.valueOf("2021-01-15"));
        Assert.assertEquals(List.of(timetable, timetable1), actual);
        when(timetableRepository.findByDate(Date.valueOf("2021-01-15"))).thenReturn(List.of());
        List<Timetable> actual1 = timetableService.findByDate(Date.valueOf("2021-01-15"));
        Assert.assertEquals(List.of(), actual1);
    }

    @Test
    @DisplayName("Find timetables by date and name")
    void findByNameAndDate() {
        when(timetableRepository.findByCourse_CourseInfo_NameAndDate("Maths", Date.valueOf("2021-01-15"))).thenReturn(List.of(timetable));
        List<Timetable> actual = timetableService.findByNameAndDate("Maths", Date.valueOf("2021-01-15"));
        Assert.assertEquals(List.of(timetable), actual);
        when(timetableRepository.findByCourse_CourseInfo_NameAndDate("Maths1", Date.valueOf("2021-01-15"))).thenReturn(List.of());
        List<Timetable> actual1 = timetableService.findByNameAndDate("Maths1", Date.valueOf("2021-01-15"));
        Assert.assertEquals(List.of(), actual1);
    }
}