package com.gsi.vacation.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeDto {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private DepartmentDto department;
    private String role;


}
