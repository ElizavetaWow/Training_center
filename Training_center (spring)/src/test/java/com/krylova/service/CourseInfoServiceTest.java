package com.krylova.service;

import com.krylova.entity.CourseInfo;
import com.krylova.repository.CourseInfoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseInfoServiceTest {

    @MockBean
    private CourseInfoRepository courseInfoRepository;
    @Autowired
    private CourseInfoService courseInfoService;

    CourseInfo courseInfo = new CourseInfo();
    CourseInfo courseInfo1 = new CourseInfo();

    @BeforeEach
    void setUp() {
        courseInfo.setId(1L);
        courseInfo.setName("Maths");
        courseInfo.setCourses(null);

        courseInfo1.setId(2L);
        courseInfo1.setName("Russian");
        courseInfo1.setCourses(null);
    }

    @Test
    @DisplayName("Create a courseInfo")
    void create() {
        when(courseInfoRepository.save(courseInfo)).thenReturn(courseInfo);
        CourseInfo actual = courseInfoService.create(courseInfo);
        Assert.assertEquals(courseInfo, actual);
    }

    @Test
    @DisplayName("Update a courseInfo")
    void update() {
        courseInfo.setName("English");
        when(courseInfoRepository.save(courseInfo)).thenReturn(courseInfo);
        CourseInfo actual = courseInfoService.update(courseInfo);
        Assert.assertEquals(courseInfo, actual);
    }

    @Test
    @DisplayName("Delete a courseInfo")
    void delete() {
        doNothing().when(courseInfoRepository).delete(courseInfo);
        boolean actual = courseInfoService.delete(courseInfo);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all courseInfos")
    void findAll() {
        when(courseInfoRepository.findAll()).thenReturn(List.of(courseInfo, courseInfo1));
        List<CourseInfo> actual = courseInfoService.findAll();
        Assert.assertEquals(List.of(courseInfo, courseInfo1), actual);
    }

    @Test
    @DisplayName("Find a courseInfo by ID")
    void findById() {
        when(courseInfoRepository.findById(1L)).thenReturn(Optional.of(courseInfo));
        Optional<CourseInfo> actual = courseInfoService.findById(1L);
        Assert.assertEquals(Optional.of(courseInfo), actual);
        when(courseInfoRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<CourseInfo> actual1 = courseInfoService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find courseInfos by name")
    void findByName() {
        when(courseInfoRepository.findByName("Maths")).thenReturn(List.of(courseInfo));
        List<CourseInfo> actual = courseInfoService.findByName("Maths");
        Assert.assertEquals(List.of(courseInfo), actual);
        when(courseInfoRepository.findByName("Maths1")).thenReturn(List.of());
        List<CourseInfo> actual1 = courseInfoService.findByName("Maths1");
        Assert.assertEquals(List.of(), actual1);
    }
}