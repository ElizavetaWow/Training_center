package com.krylova.service;

import com.krylova.entity.Administrator;
import com.krylova.repository.AdministratorRepository;
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
class AdministratorServiceTest {

    @MockBean
    private AdministratorRepository administratorRepository;
    @Autowired
    private AdministratorService administratorService;

    Administrator administrator = new Administrator();
    Administrator administrator1 = new Administrator();

    @BeforeEach
    void setUp() {
        administrator.setId(1L);
        administrator.setFirstName("Liza");
        administrator.setLastName("Ivanova");
        administrator.setEmail("liza@mail.ru");
        administrator.setPassword("123456789");
        administrator.setBirthday(Date.valueOf("1978-02-03"));

        administrator1.setId(2L);
        administrator1.setFirstName("Alisa");
        administrator1.setLastName("Batmanova");
        administrator1.setEmail("ab@mail.ru");
        administrator1.setPassword("123456789");
        administrator1.setBirthday(Date.valueOf("2001-02-03"));
    }

    @Test
    @DisplayName("Create an administrator")
    void create() {
        when(administratorRepository.save(administrator)).thenReturn(administrator);
        Administrator actual = administratorService.create(administrator);
        Assert.assertEquals(administrator, actual);
    }

    @Test
    @DisplayName("Update an administrator")
    void update() {
        administrator.setPassword("987654321");
        when(administratorRepository.save(administrator)).thenReturn(administrator);
        Administrator actual = administratorService.update(administrator);
        Assert.assertEquals(administrator, actual);
    }

    @Test
    @DisplayName("Delete an administrator")
    void delete() {
        doNothing().when(administratorRepository).delete(administrator);
        boolean actual = administratorService.delete(administrator);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all administrators")
    void findAll() {
        when(administratorRepository.findAll()).thenReturn(List.of(administrator, administrator1));
        List<Administrator> actual = administratorService.findAll();
        Assert.assertEquals(List.of(administrator, administrator1), actual);
    }

    @Test
    @DisplayName("Find an administrator by ID")
    void findById() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));
        Optional<Administrator> actual = administratorService.findById(1L);
        Assert.assertEquals(Optional.of(administrator), actual);
        when(administratorRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Administrator> actual1 = administratorService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find administrators by email")
    void findByEmail() {
        when(administratorRepository.findByEmail("liza@mail.ru")).thenReturn(List.of(administrator));
        List<Administrator> actual = administratorService.findByEmail("liza@mail.ru");
        Assert.assertEquals(List.of(administrator), actual);
        when(administratorRepository.findByEmail("lizochka@mail.ru")).thenReturn(List.of());
        List<Administrator> actual1 = administratorService.findByEmail("lizochka@mail.ru");
        Assert.assertEquals(List.of(), actual1);
    }
}