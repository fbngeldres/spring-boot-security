package com.gsi.vacation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsi.vacation.service.dto.VacationRequestCreateDto;
import com.gsi.vacation.service.dto.VacationRequestUpdateDto;
import com.gsi.vacation.service.operationresult.OperationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class VacationRequestControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("my_test_db")
            .withInitScript("init.sql")
    .withUsername("postgres")
        .withPassword("mysecretpassword");

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    private static VacationRequestCreateDto vacationCreateRequest = VacationRequestCreateDto.builder()
            .reason("Good Reason")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(1))
            .build();

    private static VacationRequestUpdateDto vacationUpdateRequest = VacationRequestUpdateDto.builder()
            .reason("Good Reason")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(50))
            .build();
    private static UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static UUID employeId= UUID.fromString("00000000-0000-0000-0000-000000000005");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE"})
    public void mustRegisterVacationOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests", companyId,employeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(vacationCreateRequest) )
                        . with(csrf())
                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationResult").value(OperationResult.SUCCESS.name()))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser
    public void mustUpdateVacationOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests/{id}", companyId,employeId, "00000000-0000-0000-0000-000000000007")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(vacationUpdateRequest) )
                        . with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationResult").value(OperationResult.SUCCESS.name()))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser
    public void mustDeleteVacationOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests/{id}", companyId,employeId, "00000000-0000-0000-0000-000000000008"
                        ). with(csrf())
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.operationResult").value(OperationResult.SUCCESS.name()))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));
    }

    @Test
    @WithMockUser
    public void mustGetVacation() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests", companyId,employeId)
                )
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser
    public void mustBeStartDateNotNull() throws Exception {

        VacationRequestCreateDto vacationCreateRequest = VacationRequestCreateDto.builder()
                .reason("Good Reason")
                .endDate(LocalDate.now().plusDays(1))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests/{id}", companyId,employeId,"00000000-0000-0000-0000-000000000007")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(vacationCreateRequest) )
                        . with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void mustBeEndDateNotNull() throws Exception {

        VacationRequestCreateDto vacationCreateRequest = VacationRequestCreateDto.builder()
                .reason("Good Reason")
                .startDate(LocalDate.now())
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/companies/{company}/employees/{employeeId}/vacation-requests", companyId,employeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(vacationCreateRequest) )
                        . with(csrf()))
                .andExpect(status().isBadRequest());
    }


}
