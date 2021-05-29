package com.krylova.service;

import com.krylova.entity.Faculty;
import com.krylova.repository.FacultyRepository;
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
class FacultyServiceTest {

    @MockBean
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyService facultyService;

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
    @DisplayName("Create a faculty")
    void create() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty actual = facultyService.create(faculty);
        Assert.assertEquals(faculty, actual);
    }

    @Test
    @DisplayName("Update a faculty")
    void update() {
        faculty.setPassword("987654321");
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty actual = facultyService.update(faculty);
        Assert.assertEquals(faculty, actual);
    }

    @Test
    @DisplayName("Delete a faculty")
    void delete() {
        doNothing().when(facultyRepository).delete(faculty);
        boolean actual = facultyService.delete(faculty);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all faculties")
    void findAll() {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty, faculty1));
        List<Faculty> actual = facultyService.findAll();
        Assert.assertEquals(List.of(faculty, faculty1), actual);
    }

    @Test
    @DisplayName("Find a faculty by ID")
    void findById() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        Optional<Faculty> actual = facultyService.findById(1L);
        Assert.assertEquals(Optional.of(faculty), actual);
        when(facultyRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Faculty> actual1 = facultyService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find faculties by email")
    void findByEmail() {
        when(facultyRepository.findByEmail("liza@mail.ru")).thenReturn(List.of(faculty));
        List<Faculty> actual = facultyService.findByEmail("liza@mail.ru");
        Assert.assertEquals(List.of(faculty), actual);
        when(facultyRepository.findByEmail("lizochka@mail.ru")).thenReturn(List.of());
        List<Faculty> actual1 = facultyService.findByEmail("lizochka@mail.ru");
        Assert.assertEquals(List.of(), actual1);
    }
}