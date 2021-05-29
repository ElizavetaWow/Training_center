package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Company;
import com.krylova.service.CompanyService;
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
class CompanyControllerTest {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

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
    void create() throws Exception {
        when(companyService.create(any())).thenReturn(company);
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(company)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all companies")
    void findAll() throws Exception {
        when(companyService.findAll()).thenReturn(List.of(company, company1));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].account", is("123456789")))
                .andExpect(jsonPath("$[0].money", is(1000)))
                .andExpect(jsonPath("$[0].name", is("Romashka")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].account", is("987654321")))
                .andExpect(jsonPath("$[1].money", is(2300)))
                .andExpect(jsonPath("$[1].name", is("Rosa")));
    }

    @Test
    @DisplayName("Find a company by ID")
    void find() throws Exception {
        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.account", is("123456789")))
                .andExpect(jsonPath("$.money", is(1000)))
                .andExpect(jsonPath("$.name", is("Romashka")));
    }


    @Test
    @DisplayName("Find companies by name")
    void findByName() throws Exception {
        when(companyService.findByName("Romashka")).thenReturn(List.of(company));
        mockMvc.perform(get("/companies/n_Romashka"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].account", is("123456789")))
                .andExpect(jsonPath("$[0].money", is(1000)))
                .andExpect(jsonPath("$[0].name", is("Romashka")));
    }

    @Test
    @DisplayName("Update a company")
    void updateCompany() throws Exception {
        Company company2 = company;
        company2.setMoney(100);
        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        when(companyService.update(company)).thenReturn(company2);

        mockMvc.perform(put("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(company2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.account", is("123456789")))
                .andExpect(jsonPath("$.money", is(100)))
                .andExpect(jsonPath("$.name", is("Romashka")));
    }

    @Test
    @DisplayName("Delete a company")
    void deleteCompany() throws Exception {
        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        when(companyService.delete(company)).thenReturn(true);

        mockMvc.perform(delete("/companies/{id}", 1))
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