package com.krylova.service;

import com.krylova.entity.Company;
import com.krylova.entity.Course;
import com.krylova.repository.CourseRepository;
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
class CourseServiceTest {

    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

    Course course = new Course();
    Course course1 = new Course();
    Company company = new Company();

    @BeforeEach
    void setUp() {
        course.setId(1L);
        course.setPrice(1000);
        course.setStartDate(Date.valueOf("2020-02-03"));
        course.setFinishDate(Date.valueOf("2021-02-03"));
        course.setCourseInfo(null);
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
    void create() {
        when(courseRepository.save(course)).thenReturn(course);
        Course actual = courseService.create(course);
        Assert.assertEquals(course, actual);
    }

    @Test
    @DisplayName("Update a course")
    void update() {
        course.setPrice(500);
        when(courseRepository.save(course)).thenReturn(course);
        Course actual = courseService.update(course);
        Assert.assertEquals(course, actual);
    }

    @Test
    @DisplayName("Delete a course")
    void delete() {
        doNothing().when(courseRepository).delete(course);
        boolean actual = courseService.delete(course);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all courses")
    void findAll() {
        when(courseRepository.findAll()).thenReturn(List.of(course, course1));
        List<Course> actual = courseService.findAll();
        Assert.assertEquals(List.of(course, course1), actual);
    }

    @Test
    @DisplayName("Find a course by ID")
    void findById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Optional<Course> actual = courseService.findById(1L);
        Assert.assertEquals(Optional.of(course), actual);
        when(courseRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Course> actual1 = courseService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find courses by courseInfo")
    void findByCourseInfo() {
        when(courseRepository.findByCourseInfoName("maths")).thenReturn(List.of(course));
        List<Course> actual = courseService.findByCourseInfo("maths");
        Assert.assertEquals(List.of(course), actual);
        when(courseRepository.findByCourseInfoName("maths1")).thenReturn(List.of());
        List<Course> actual1 = courseService.findByCourseInfo("maths1");
        Assert.assertEquals(List.of(), actual1);
    }

}