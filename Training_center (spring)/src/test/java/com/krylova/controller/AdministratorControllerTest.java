package com.krylova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylova.entity.Administrator;
import com.krylova.service.AdministratorService;
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
class AdministratorControllerTest {

    @MockBean
    private AdministratorService administratorService;

    @Autowired
    private MockMvc mockMvc;

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
    void create() throws Exception {
        when(administratorService.create(any())).thenReturn(administrator);
        mockMvc.perform(post("/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(administrator)))
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all administrators")
    void findAll() throws Exception {
        when(administratorService.findAll()).thenReturn(List.of(administrator, administrator1));
        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Alisa")))
                .andExpect(jsonPath("$[1].lastName", is("Batmanova")))
                .andExpect(jsonPath("$[1].email", is("ab@mail.ru")))
                .andExpect(jsonPath("$[1].password", is("123456789")))
                .andExpect(jsonPath("$[1].birthday", is("2001-02-03")));
    }

    @Test
    @DisplayName("Find an administrator by ID")
    void find() throws Exception {
        when(administratorService.findById(1L)).thenReturn(Optional.of(administrator));
        mockMvc.perform(get("/admins/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Liza")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Find administrators by email")
    void findByEmail() throws Exception {
        when(administratorService.findByEmail("liza@mail.ru")).thenReturn(List.of(administrator));
        mockMvc.perform(get("/admins/email_liza@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Liza")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanova")))
                .andExpect(jsonPath("$[0].email", is("liza@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123456789")))
                .andExpect(jsonPath("$[0].birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Update an administrator")
    void updateAdministrator() throws Exception {
        Administrator administrator2 = administrator;
        administrator2.setFirstName("Vera");
        when(administratorService.findById(1L)).thenReturn(Optional.of(administrator));
        when(administratorService.update(administrator)).thenReturn(administrator2);

        mockMvc.perform(put("/admins/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(toJSON(administrator2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Vera")))
                .andExpect(jsonPath("$.lastName", is("Ivanova")))
                .andExpect(jsonPath("$.email", is("liza@mail.ru")))
                .andExpect(jsonPath("$.password", is("123456789")))
                .andExpect(jsonPath("$.birthday", is("1978-02-03")));
    }

    @Test
    @DisplayName("Delete an administrator")
    void deleteAdministrator() throws Exception {
        when(administratorService.findById(1L)).thenReturn(Optional.of(administrator));
        when(administratorService.delete(administrator)).thenReturn(true);

        mockMvc.perform(delete("/admins/{id}", 1))
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