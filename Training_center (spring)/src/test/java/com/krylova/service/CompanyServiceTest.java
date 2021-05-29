package com.krylova.service;

import com.krylova.entity.Company;
import com.krylova.repository.CompanyRepository;
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
class CompanyServiceTest {

    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;

    Company company = new Company();
    Company company1 = new Company();

    @BeforeEach
    void setUp() {
        company.setId(1L);
        company.setAccount("123456789");
        company.setMoney(1000);
        company.setName("Romashka");

        company1.setId(2L);
        company1.setAccount("987654321");
        company1.setMoney(2300);
        company1.setName("Rosa");
    }

    @Test
    @DisplayName("Create a company")
    void create() {
        when(companyRepository.save(company)).thenReturn(company);
        Company actual = companyService.create(company);
        Assert.assertEquals(company, actual);
    }

    @Test
    @DisplayName("Update a company")
    void update() {
        company.setMoney(500);
        when(companyRepository.save(company)).thenReturn(company);
        Company actual = companyService.update(company);
        Assert.assertEquals(company, actual);
    }

    @Test
    @DisplayName("Delete a company")
    void delete() {
        doNothing().when(companyRepository).delete(company);
        boolean actual = companyService.delete(company);
        Assert.assertTrue(actual);
    }

    @Test
    @DisplayName("Find all companies")
    void findAll() {
        when(companyRepository.findAll()).thenReturn(List.of(company, company1));
        List<Company> actual = companyService.findAll();
        Assert.assertEquals(List.of(company, company1), actual);
    }

    @Test
    @DisplayName("Find a company by ID")
    void findById() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Optional<Company> actual = companyService.findById(1L);
        Assert.assertEquals(Optional.of(company), actual);
        when(companyRepository.findById(122L)).thenReturn(Optional.empty());
        Optional<Company> actual1 = companyService.findById(122L);
        Assert.assertEquals(Optional.empty(), actual1);
    }

    @Test
    @DisplayName("Find companies by name")
    void findByName() {
        when(companyRepository.findByName("Romashka")).thenReturn(List.of(company));
        List<Company> actual = companyService.findByName("Romashka");
        Assert.assertEquals(List.of(company), actual);
        when(companyRepository.findByName("Romashka1")).thenReturn(List.of());
        List<Company> actual1 = companyService.findByName("Romashka1");
        Assert.assertEquals(List.of(), actual1);
    }

}