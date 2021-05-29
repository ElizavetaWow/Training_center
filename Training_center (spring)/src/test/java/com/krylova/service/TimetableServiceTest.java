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

import java.time.LocalDate;
import java.time.LocalTime;
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
        timetable.setDate(LocalDate.of(2021, 1, 15));
        timetable.setTime(LocalTime.of(15, 30));
        timetable.setCourse(null);
        timetable.setPlace(null);

        timetable1.setId(2L);
        timetable1.setDate(LocalDate.of(2021, 1, 15));
        timetable1.setTime(LocalTime.of(21, 0));
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
        timetable.setTime(LocalTime.of(16, 0));
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
        when(timetableRepository.findByDate(LocalDate.of(2021, 1, 15))).thenReturn(List.of(timetable, timetable1));
        List<Timetable> actual = timetableService.findByDate(LocalDate.of(2021, 1, 15));
        Assert.assertEquals(List.of(timetable, timetable1), actual);
        when(timetableRepository.findByDate(LocalDate.of(2020, 1, 15))).thenReturn(List.of());
        List<Timetable> actual1 = timetableService.findByDate(LocalDate.of(2020, 1, 15));
        Assert.assertEquals(List.of(), actual1);
    }

    @Test
    @DisplayName("Find timetables by date and name")
    void findByNameAndDate() {
        when(timetableRepository.findByCourse_CourseInfo_NameAndDate("Maths", LocalDate.of(2021, 1, 15))).thenReturn(List.of(timetable));
        List<Timetable> actual = timetableService.findByNameAndDate("Maths", LocalDate.of(2021, 1, 15));
        Assert.assertEquals(List.of(timetable), actual);
        when(timetableRepository.findByCourse_CourseInfo_NameAndDate("Maths1", LocalDate.of(2021, 1, 15))).thenReturn(List.of());
        List<Timetable> actual1 = timetableService.findByNameAndDate("Maths1", LocalDate.of(2021, 1, 15));
        Assert.assertEquals(List.of(), actual1);
    }
}